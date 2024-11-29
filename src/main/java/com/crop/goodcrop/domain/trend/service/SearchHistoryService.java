package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.config.RedisConfig;
import com.crop.goodcrop.domain.trend.entity.SearchHistory;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public void putCacheData(Long memberId, String keyword) {
        String key = memberId + "_" + LocalDateTime.now();
        SearchHistory searchHistory = SearchHistory.builder()
                .memberId(memberId)
                .keyword(keyword)
                .createdAt(LocalDateTime.now())
                .build();
        redisTemplate.opsForHash().put(RedisConfig.SEARCH_HISTORY, key, searchHistory);
    }

    @Transactional
    public void writeBack() {
        Map<Object, Object> cacheMap = redisTemplate
                .opsForHash()
                .entries(RedisConfig.SEARCH_HISTORY);

        List<SearchHistory> searchHistories = new ArrayList<>();
        for (Object key : cacheMap.keySet())
            searchHistories.add((SearchHistory) cacheMap.get(key));
        searchHistoryRepository.saveAll(searchHistories);
        clearCache();
    }

    public Map<Object, Object> getAllCacheData() {
        return redisTemplate.opsForHash().entries(RedisConfig.SEARCH_HISTORY);
    }

    // 캐시 비우기
    public void clearCache() {
        redisTemplate.delete(RedisConfig.SEARCH_HISTORY);
    }
}
