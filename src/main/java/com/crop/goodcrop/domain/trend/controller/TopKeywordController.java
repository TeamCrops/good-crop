package com.crop.goodcrop.domain.trend.controller;

import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.service.TopKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TopKeywordController {
    private final TopKeywordService topKeywordService;

    @GetMapping("/v1/trends")
    public ResponseEntity<List<TopKeywordDto>> retrieveTopKeywordVersion1() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topKeywordService.retrieveTopKeywordVersion1());
    }

    @GetMapping("/v2/trends")
    public ResponseEntity<List<TopKeywordDto>> retrieveTopKeywordVersion2() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topKeywordService.retrieveTopKeywordVersion2());
    }

    @PostMapping("/trends")
    public ResponseEntity<Void> refreshTopKeywordVersion1() {
        topKeywordService.refreshTopKeyword();
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
