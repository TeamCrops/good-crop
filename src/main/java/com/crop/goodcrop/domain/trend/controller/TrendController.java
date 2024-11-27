package com.crop.goodcrop.domain.trend.controller;

import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TrendController {
    private final TrendService trendService;

    @GetMapping("/v1/trends")
    public ResponseEntity<List<TopKeywordDto>> retrieveTopKeywordVersion1() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trendService.retrieveTopKeywordVersion1());
    }

    @GetMapping("/v2/trends")
    public ResponseEntity<List<TopKeywordDto>> retrieveTopKeywordVersion2() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trendService.retrieveTopKeywordVersion2());
    }

    @PostMapping("/trends")
    public ResponseEntity<Void> modifyTopKeyword() {
        trendService.modifyTopKeyword();
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
