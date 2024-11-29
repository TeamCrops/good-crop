package com.crop.goodcrop.domain.like.controller;

import com.crop.goodcrop.domain.like.dto.request.LikeRequestDto;
import com.crop.goodcrop.domain.like.service.LikeService;
import com.crop.goodcrop.security.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{productId}")
    public ResponseEntity<Void> createLike(
            @AuthenticationPrincipal UserDetailsImpl userDetail,
            @PathVariable Long productId,
            @RequestBody LikeRequestDto requestDto) {
        likeService.createLike(userDetail, requestDto, productId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteLike(
            @AuthenticationPrincipal UserDetailsImpl userDetail,
            @PathVariable Long productId,
            @RequestBody LikeRequestDto requestDto
    ) {
        likeService.deleteLike(userDetail, requestDto, productId);
        return ResponseEntity.noContent().build();
    }
}
