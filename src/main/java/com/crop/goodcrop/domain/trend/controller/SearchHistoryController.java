package com.crop.goodcrop.domain.trend.controller;

import com.crop.goodcrop.domain.trend.service.SearchHistoryService;
import com.crop.goodcrop.domain.trend.service.WriteBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchHistoryController {
    //임시의 컨트롤러
    private final SearchHistoryService searchHistoryService;
    private final WriteBackService writeBackService;

    // 검색 로그 저장 (캐시)
    @PostMapping("/log")
    public String logSearch(@RequestParam Long memberId, @RequestParam String keyword) {
        searchHistoryService.logSearch(memberId, keyword);
        return "검색 로그 저장 완료: " + keyword;
    }

    // DB에 한 번에 write-back
    @PostMapping("/write-back")
    public String writeBackLogs() {
        writeBackService.writeBackLogs();
        return "DB에 write-back 완료";
    }

    // 캐시 비우기
    @PostMapping("/clear-cache")
    public String clearCache() {
        writeBackService.clearCache();
        return "캐시가 비워졌습니다.";
    }

    // 캐시에 저장된 모든 검색 로그 반환
    @GetMapping("/logs/cache")
    public Map<Object, Object> getAllLogsFromCache() {
        return searchHistoryService.getAllCacheData();
    }

    //writeback을 사용해서 db에 과부하가 걸렸는데 성능이 좋아진 수치를 보고 싶다
    //상품검색을 100번

}
