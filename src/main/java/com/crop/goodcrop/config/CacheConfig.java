package com.crop.goodcrop.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {
    //public static final String PRODUCT = "product";
    public static final String SEARCH_HISTORY = "searchHistory";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES) // 5분 후 캐시 만료
                .maximumSize(1000)); // 캐시 최대 크기
        return cacheManager;
    }

    @Bean
    public CaffeineCache searchHistoryCache() {
        return new CaffeineCache("searchHistory", Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .build());
    }

//    @Bean
//    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
//        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(SEARCH_HISTORY);
//        caffeineCacheManager.setCaffeine(caffeine);
//        return caffeineCacheManager;
//    }
//
//    @Bean
//    public Caffeine<Object, Object> caffeineConfig() {
//        return Caffeine.newBuilder()
//                .expireAfterWrite(5, TimeUnit.MINUTES) // 5분 후 캐시 만료
//                .maximumSize(10_000); // 캐시 최대 크기
//    }
}