package com.crop.goodcrop.domain.review.controller;

import com.crop.goodcrop.domain.common.dto.PageResponseDto;
import com.crop.goodcrop.domain.review.dto.request.ReviewCreateRequestDto;
import com.crop.goodcrop.domain.review.dto.request.ReviewModifyRequestDto;
import com.crop.goodcrop.domain.review.dto.response.ReviewResponseDto;
import com.crop.goodcrop.domain.review.entity.Review;
import com.crop.goodcrop.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<ReviewResponseDto> writeReview(
            // TODO
            //  - Security 적용
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long memberId,
            @PathVariable Long productId,
            @Valid @RequestBody ReviewCreateRequestDto reviewRequestDto
    ) {
//        Long memberId = userDetails.getMember().getId();
        ReviewResponseDto reviewResponseDto = reviewService.createReview(memberId, productId, reviewRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<PageResponseDto<Review>> readReviews(
            @PathVariable Long productId,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        PageResponseDto<Review> responses = reviewService.retrieveReviews(productId, page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            // TODO
            //  - Security 적용
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long memberId,
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewModifyRequestDto reviewRequestDto
    ) {
//        Long memberId = userDetails.getMember().getId();
        ReviewResponseDto reviewResponseDto = reviewService.modifyReview(memberId, productId, reviewId, reviewRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewResponseDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> deleteReview(
            // TODO
            //  - Security 적용
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long memberId,
            @PathVariable Long productId,
            @PathVariable Long reviewId
    ) {
//        Long memberId = userDetails.getMember().getId();
        reviewService.deleteReview(memberId, productId, reviewId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
