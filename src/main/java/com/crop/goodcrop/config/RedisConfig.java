package com.crop.goodcrop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching  // 캐시 기능 활성화
public class RedisConfig {
    public static final String PRODUCT = "product";
    public static final String TOP_KEYWORD = "topKeyword";
    public static final String SEARCH_HISTORY = "searchHistory";

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.cache.redis.time-to-live}")
    private Long timeToLive;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis Standalone 설정
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host); // Redis 서버 호스트
        config.setPort(port); // Redis 서버 포트
        config.setPassword(password); // Redis 비밀번호 (필요한 경우 설정)

        // LettuceConnectionFactory를 사용해 Redis와 연결
        return new LettuceConnectionFactory(config);
    }

    /**
     * RedisTemplate 설정
     *
     * @param connectionFactory RedisConnectionFactory 빈 주입
     * @return RedisTemplate 인스턴스
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jsonRedisSerializer);
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
                )
                .entryTtl(Duration.ofMinutes(5)); // TTL 설정 (5분)

        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration(PRODUCT, productCacheConfiguration())
                .withCacheConfiguration(SEARCH_HISTORY, searchHistoryCacheConfiguration())
                .withCacheConfiguration(TOP_KEYWORD, topKeywordCacheConfiguration())
                .cacheDefaults(cacheConfig)
                .build();
    }

    // product 캐시 설정
    private RedisCacheConfiguration productCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(timeToLive))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    // searchHistory 캐시 설정
    private RedisCacheConfiguration searchHistoryCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(2)) // 2시간 후 만료
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    // topKeyword 캐시 설정
    private RedisCacheConfiguration topKeywordCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(2)) // 2시간 후 만료
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
