package com.crop.goodcrop.config;

import com.crop.goodcrop.cache.CacheType;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = Arrays.stream(CacheType.values())
                .map(cache -> new CaffeineCache(
                        cache.getCacheName(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(cache.getExpiredAfterWrite(), TimeUnit.HOURS)
                                .maximumSize(cache.getMaximumSize())
                                .recordStats()
                                .build()
                ))
                .toList();

        cacheManager.setCaches(caches);
        return cacheManager;
    }
}