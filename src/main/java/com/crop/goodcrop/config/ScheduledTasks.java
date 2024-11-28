package com.crop.goodcrop.config;

import com.crop.goodcrop.domain.trend.service.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ScheduledTasks {
    private final SearchHistoryService searchHistoryService;

    // 5분마다 캐시 데이터를 DB에 write-back하고, 캐시를 비웁니다.
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void writeBackAndClearCache() {
        searchHistoryService.writeBack();
        searchHistoryService.clearCache();
    }
}
