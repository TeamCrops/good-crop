package com.crop.goodcrop.domain.like.controller;

import com.crop.goodcrop.domain.like.dto.request.LikeRequestDto;
import com.crop.goodcrop.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> createLike(
            @PathVariable Long productId,
            @RequestBody LikeRequestDto requestDto) {
        likeService.createLike(requestDto, productId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteLike(
            @PathVariable Long productId,
            @RequestBody LikeRequestDto requestDto
    ) {
        likeService.deleteLike(requestDto, productId);
        return ResponseEntity.noContent().build();
    }
}
