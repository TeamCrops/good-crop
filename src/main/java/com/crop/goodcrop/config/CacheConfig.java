package com.crop.goodcrop.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration // 설정 클래스임을 명시
@EnableCaching  // 캐시 기능 활성화
public class CacheConfig {
    public static final String PRODUCT = "product";
    public static final String TOP_KEYWORD = "searchHistory";

    /**
     * CaffeineCacheManager 생성.
     * Caffeine 설정을 적용하여 캐시 관리자를 반환합니다.
     *
     * @return CacheManager 인스턴스
     */
    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache(PRODUCT, productCacheBuilder());
        cacheManager.registerCustomCache(TOP_KEYWORD,searchHistoryCacheBuilder());
        return cacheManager;
    }

    private Cache<Object, Object> searchHistoryCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS) // 2시간 후 만료
                .maximumSize(50000)
                .build();
    }

    private Cache<Object, Object> productCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS) // 2시간 후 만료
                .maximumSize(50000)
                .build();
    }
}