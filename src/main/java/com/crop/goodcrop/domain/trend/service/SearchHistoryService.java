package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.config.RedisConfig;
import com.crop.goodcrop.domain.trend.entity.SearchHistory;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final CacheManager cacheManager;
    private final SearchHistoryRepository searchHistoryRepository;

    public void putCacheData(Long memberId, String keyword) {
        Cache cache = cacheManager.getCache(RedisConfig.SEARCH_HISTORY);
        if(cache!=null){
            List<String> keywords = cache.get(memberId, ArrayList::new);
            keywords.add(keyword);
            cache.put(memberId, keywords);
        }
    }

    @Scheduled(fixedRate = 300000)
    // @Scheduled(fixedRate = 1000 * 30) // 30초
    @Transactional
    public void writeBack(){
        // 레디스 캐시에서 캐시를 가져오기
        Cache cache = cacheManager.getCache(RedisConfig.SEARCH_HISTORY);
        if (cache != null) {
            // Map 형태로 가져오기
            Map<Object, Object> cacheMap = getAllCacheData();
            List<SearchHistory> histories = new ArrayList<>();
            // Map 순회
            for (Map.Entry<Object, Object> entry : cacheMap.entrySet()) {
                Long memberId = Long.valueOf(entry.getKey().toString());
                List<String> keywords = (List<String>) entry.getValue();
//                histories.add((SearchHistory)entry.getValue());

                for(String keyword : keywords){
                    histories.add(SearchHistory.builder()
                            .memberId(memberId)
                            .keyword(keyword)
                            .build());
                }

                // DB에 저장
                searchHistoryRepository.saveAll(histories);
            }

            // 캐시 초기화
            cache.clear();
        }
    }

    public Map<Object, Object> getAllCacheData() {
        Cache cache = cacheManager.getCache(RedisConfig.SEARCH_HISTORY);
        if (cache == null) {
            return new HashMap<>(); // 캐시가 없을 경우 빈 Map 반환
        }
        // Native Cache를 통해 데이터 접근
        if (cache.getNativeCache() instanceof RedisCache redisCache) {
            return (Map<Object, Object>) redisCache.getNativeCache();
        }
        return new HashMap<>(); // 캐시 유형이 Caffeine이 아닐 경우 빈 Map 반환
    }

    // 캐시 비우기
    public void clearCache() {
        Cache cache = cacheManager.getCache(RedisConfig.SEARCH_HISTORY);
        if (cache != null) {
            cache.clear();  // 캐시 비우기
        }
    }
}
