package com.crop.goodcrop.domain.trend.service;


import com.crop.goodcrop.config.RedisConfig;
import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.entity.TopKeyword;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import com.crop.goodcrop.domain.trend.repository.TopKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopKeywordService {
    private final int TOP_KEYWORD_COUNT = 5;
    private final CacheManager cacheManager;
    private final SearchHistoryRepository searchHistoryRepository;
    private final TopKeywordRepository topKeywordRepository;

    public List<TopKeywordDto> retrieveTopKeywordVersion1() {
        List<TopKeyword> topKeywords = topKeywordRepository.findAll();
        return topKeywords.stream().map(TopKeywordDto::from).toList();
    }

    @Cacheable(value = RedisConfig.TOP_KEYWORD)
    public List<TopKeywordDto> retrieveTopKeywordVersion2() {
        List<TopKeyword> topKeywords = topKeywordRepository.findAll();
        return topKeywords.stream().map(TopKeywordDto::from).toList();
    }

    @Transactional
    @Scheduled(fixedRate = 120000) // 2시간
    // @Scheduled(fixedRate = 1000 * 30) // 30초
    public void refreshTopKeyword() {
        List<TopKeywordDto> topKeywords = searchHistoryRepository.findTopFiveOrderBySearchCount();
        List<TopKeyword> newTopKeywords = convertDtoToEntity(topKeywords);
        if(newTopKeywords.isEmpty())
            return;

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

    public void clearCache() {
        Cache cache = cacheManager.getCache(RedisConfig.TOP_KEYWORD);
        if (cache != null) {
            cache.clear();  // 캐시 비우기
        }
    }
}
