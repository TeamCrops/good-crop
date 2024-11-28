//package com.crop.goodcrop.cacheFile;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.caffeine.CaffeineCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.TimeUnit;
//
//@EnableCaching
//@Configuration
//public class CacheConfig {
//
//    @Bean
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                .expireAfterWrite(10, TimeUnit.MINUTES)  // 예시: 10분 후 캐시 만료
//                .maximumSize(100));  // 예시: 캐시 최대 크기 100개
//        return cacheManager;
//    }
//}
