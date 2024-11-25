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
    public ResponseEntity<Void> retrieveLike(
            @PathVariable Long productId,
            @RequestBody LikeRequestDto requestDto) {
        likeService.retrieveLike(requestDto, productId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }

//    @DeleteMapping("/{productId}")
//    public ResponseEntity<Void> removeLike(
//            @PathVariable Long productId,
//            @RequestBody LikeRequestDto requestDto
//    ) {
//        likeService.removeLike(requestDto, productId);
//        return ResponseEntity.noContent().build();
//    }
}
