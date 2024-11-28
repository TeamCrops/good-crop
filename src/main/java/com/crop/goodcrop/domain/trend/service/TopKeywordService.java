package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.config.CacheConfig;
import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.entity.TopKeyword;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import com.crop.goodcrop.domain.trend.repository.TopKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopKeywordService {
    private final int TOP_KEYWORD_COUNT = 5;
    private final SearchHistoryRepository searchHistoryRepository;
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
    public void refreshTopKeyword() {
        List<TopKeywordDto> topKeywords = searchHistoryRepository.findTopFiveOrderBySearchCount();
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
}
