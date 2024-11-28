package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.config.CacheConfig;
import com.crop.goodcrop.domain.trend.entity.SearchHistory;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class WriteBackService {
    private final CacheManager cacheManager;
    private final SearchHistoryRepository searchHistoryRepository;

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void writeBackLogs(){
        // Caffeine 캐시에서 캐시를 가져오기
        CaffeineCache cache = (CaffeineCache) cacheManager.getCache(CacheConfig.SEARCH_HISTORY);
        if (cache != null) {
            // CaffeineCache에서 asMap() 메서드로 데이터를 Map 형태로 가져오기
            Map<Object, Object> cacheMap = cache.getNativeCache().asMap();

            // Map 순회
            for (Map.Entry<Object, Object> entry : cacheMap.entrySet()) {
                Long memberId = (Long) entry.getKey();
                List<String> keywords = (List<String>) entry.getValue();

                // DB에 저장
                for (String keyword : keywords) {
                    searchHistoryRepository.save(
                            SearchHistory.builder()
                                    .memberId(memberId)
                                    .keyword(keyword)
                                    .build()
                    );
                }
            }

            // 캐시 초기화
            cache.clear();
        }
    }

    // 캐시 비우기
    public void clearCache() {
        Cache cache = cacheManager.getCache(CacheConfig.SEARCH_HISTORY);
        if (cache != null) {
            cache.clear();  // 캐시 비우기
        }
    }

}
