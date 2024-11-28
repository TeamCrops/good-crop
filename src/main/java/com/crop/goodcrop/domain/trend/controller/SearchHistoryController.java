package com.crop.goodcrop.domain.trend.controller;

import com.crop.goodcrop.domain.trend.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    // 검색 로그 저장 (캐시)
    @PostMapping("/cache/search-history")
    public String putCacheData(@RequestParam Long memberId, @RequestParam String keyword) {
        searchHistoryService.putCacheData(memberId, keyword);
        return "검색 로그 저장 완료: " + keyword;
    }

    // DB에 한 번에 write-back
    @PostMapping("/cache/search-history/write-back")
    public String writeBack() {
        searchHistoryService.writeBack();
        return "DB에 write-back 완료";
    }

    @GetMapping("/cache/search-history")
    public Map<Object, Object> getAllCacheData() {
        return searchHistoryService.getAllCacheData();
    }

//    // 캐시 비우기
//    @PostMapping("/cache/search-history/clear")
//    public String clearCache() {
//        searchHistoryService.clearCache();
//        return "캐시가 비워졌습니다.";
//    }
}
