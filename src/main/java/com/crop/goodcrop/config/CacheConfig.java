package com.crop.goodcrop.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {
    public static final String PRODUCT = "product";
    public static final String SEARCH_HISTORY = "searchHistory";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(PRODUCT, SEARCH_HISTORY);
    }
}