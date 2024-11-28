package com.crop.goodcrop.trend;

import com.crop.goodcrop.config.CaffeineCacheConfig;
import com.crop.goodcrop.domain.trend.entity.SearchHistory;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import com.crop.goodcrop.domain.trend.service.SearchHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class SearchHistoryTest {
    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testSearchLogWriteBack() {
        searchHistoryRepository.deleteAll();
        // 1. 검색 로그 캐쉬에 저장
        searchHistoryService.putCacheData(1L, "keyword1");
        searchHistoryService.putCacheData(2L, "keyword2");
        searchHistoryService.putCacheData(3L, "keyword3");

        // 2. 캐시에 로그 저장 확인
        Cache cache = cacheManager.getCache(CaffeineCacheConfig.SEARCH_HISTORY);
        List<String> keywordsFromCache1 = cache.get(1L, ArrayList::new);
        List<String> keywordsFromCache2 = cache.get(2L, ArrayList::new);
        List<String> keywordsFromCache3 = cache.get(3L, ArrayList::new);

        // 3. 캐시에서 로그 저장 확인
        assertTrue(keywordsFromCache1.contains("keyword1"), "keyword1가 캐시에 저장되지 않았거나 잘못된 값이 저장되었습니다.");
        assertTrue(keywordsFromCache2.contains("keyword2"), "keyword2가 캐시에 저장되지 않았거나 잘못된 값이 저장되었습니다.");
        assertTrue(keywordsFromCache3.contains("keyword3"), "keyword3가 캐시에 저장되지 않았거나 잘못된 값이 저장되었습니다.");

        // 4. DB에 한 번에 insert
        // 5분 후 writeBack 실행 예정
        searchHistoryService.writeBack();

        // 5. DB에 저장 확인
        List<SearchHistory> logs = searchHistoryRepository.findAll();
        assertEquals("DB에 저장된 로그의 수가 예상과 다릅니다.",3, logs.size());
        logs.forEach(log -> System.out.println("DB에 저장된 로그: " + log.getKeyword()));


        // 캐시에서 확인 (캐시 비움 확인)
        List<String> emptyCache = cache.get(1L, ArrayList::new);
        assertTrue(emptyCache.isEmpty(), "캐시가 비워지지 않았습니다.");

    }
}
