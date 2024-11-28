package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.config.CacheConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final CacheManager cacheManager;

    public void logSearch(Long memberId, String keyword) {
        Cache cache = cacheManager.getCache(CacheConfig.SEARCH_HISTORY);

        if(cache!=null){
            List<String> keywords = cache.get(memberId, ArrayList::new);
            keywords.add(keyword);
            cache.put(memberId, keywords);
        }
    }

    // 캐시에 저장된 모든 데이터 확인
    public Map<Object, Object> getAllCacheData() {
        Cache cache = cacheManager.getCache(CacheConfig.SEARCH_HISTORY);
        if (cache == null) {
            return new HashMap<>(); // 캐시가 없을 경우 빈 Map 반환
        }
        // Native Cache를 통해 데이터 접근
        if (cache.getNativeCache() instanceof com.github.benmanes.caffeine.cache.Cache) {
            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                    (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cache.getNativeCache();
            return nativeCache.asMap();
        }
        return new HashMap<>(); // 캐시 유형이 Caffeine이 아닐 경우 빈 Map 반환
    }
}
