package com.crop.goodcrop.domain.trend.controller;

import com.crop.goodcrop.domain.trend.dto.TrendResponseDto;
import com.crop.goodcrop.domain.trend.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrendController {
    private final TrendService trendService;

    @GetMapping("/v1/trends")
    public ResponseEntity<List<TrendResponseDto>> retrieveTrend() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trendService.retrieveTrend());
    }
}
