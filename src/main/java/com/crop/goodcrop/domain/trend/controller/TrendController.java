package com.crop.goodcrop.domain.trend.controller;

import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrendController {
    private final TrendService trendService;

    @PutMapping("/v1/trends")
    public ResponseEntity<Void> modifyTopKeyword() {
        trendService.modifyTopKeyword();
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/trends")
    public ResponseEntity<List<TopKeywordDto>> retrieveTopKeyword() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trendService.retrieveTopKeyword());
    }
}
