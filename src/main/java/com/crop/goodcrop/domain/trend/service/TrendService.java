package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.config.CacheConfig;
import com.crop.goodcrop.config.H2Config;
import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.entity.h2.H2SearchHistory;
import com.crop.goodcrop.domain.trend.entity.mysql.SearchHistory;
import com.crop.goodcrop.domain.trend.entity.mysql.TopKeyword;
import com.crop.goodcrop.domain.trend.repository.h2.H2SearchHistoryRepository;
import com.crop.goodcrop.domain.trend.repository.mysql.SearchHistoryRepository;
import com.crop.goodcrop.domain.trend.repository.mysql.TopKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendService {
    private final int TOP_KEYWORD_COUNT = 10;
    private final SearchHistoryRepository searchHistoryRepository;
    private final H2SearchHistoryRepository h2SearchHistoryRepository;
    private final TopKeywordRepository topKeywordRepository;

    public List<TopKeywordDto> retrieveTopKeywordVersion1() {
        List<TopKeyword> topKeywords = topKeywordRepository.findAll();
        return topKeywords.stream().map(TopKeywordDto::from).toList();
    }

    @Cacheable(value = CacheConfig.TOP_KEYWORD)
    public List<TopKeywordDto> retrieveTopKeywordVersion2() {
        List<TopKeyword> topKeywords = topKeywordRepository.findAll();
        return topKeywords.stream().map(TopKeywordDto::from).toList();
    }

    @Transactional
    public void modifyTopKeywordVersion1() {
        List<TopKeywordDto> topKeywords = searchHistoryRepository.findTopFiveOrderBySearchCount();
        modifyTopKeyword(topKeywords);
    }

    @Transactional
    // @Scheduled(fixedDelay = H2Config.MODIFY_DURATION)
    public void modifyTopKeywordVersion2() {
        List<TopKeywordDto> topKeywords = h2SearchHistoryRepository.findTopKeywordOrderBySearchCount();
        modifyTopKeyword(topKeywords);
    }

    private void modifyTopKeyword(List<TopKeywordDto> topKeywords) {
        List<TopKeyword> newTopKeywords = convertDtoToEntity(topKeywords);
        if(newTopKeywords.size() < TOP_KEYWORD_COUNT)
            addTopKeyword(newTopKeywords);

        topKeywordRepository.deleteAll();
        topKeywordRepository.saveAll(newTopKeywords);
    }

    private List<TopKeyword> convertDtoToEntity(List<TopKeywordDto> topKeywords) {
        List<TopKeyword> newTopKeywords = new ArrayList<>();
        for (TopKeywordDto topKeywordDto : topKeywords) {
            TopKeyword topKeyword = topKeywordDto.convertDtoToEntity();
            newTopKeywords.add(topKeyword);
        }
        return newTopKeywords;
    }

    private void addTopKeyword(List<TopKeyword> newTopKeywords) {
        List<TopKeyword> topKeywords = topKeywordRepository.findAll();
        for (int idx = 0; idx < TOP_KEYWORD_COUNT - newTopKeywords.size(); idx++) {
            boolean isSame = false;
            for(TopKeyword topKeyword : topKeywords) {
                if(topKeyword.getKeyword().equals(newTopKeywords.get(idx).getKeyword())) {
                    isSame = true;
                    break;
                }
            }

            if(!isSame)
                topKeywords.add(newTopKeywords.get(idx));

            if(topKeywords.size() == TOP_KEYWORD_COUNT)
                break;
        }
    }

    @Scheduled(fixedDelay = H2Config.MIGRATION_DURATION)
    public void migration() {
        List<H2SearchHistory> h2SearchHistories = h2SearchHistoryRepository.findAll();
        List<SearchHistory> searchHistories = h2SearchHistories.stream()
                .map(item -> {
                    return SearchHistory.builder()
                            .id(item.getId())
                            .keyword(item.getKeyword())
                            .memberId(item.getMemberId())
                            .createdAt(item.getCreatedAt())
                            .build();
                }).toList();
        searchHistoryRepository.saveAll(searchHistories);
    }
}
