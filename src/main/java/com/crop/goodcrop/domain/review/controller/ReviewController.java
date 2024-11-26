package com.crop.goodcrop.domain.review.controller;

import com.crop.goodcrop.domain.common.dto.PageResponseDto;
import com.crop.goodcrop.domain.review.dto.request.ReviewRequestDto;
import com.crop.goodcrop.domain.review.dto.response.ReviewResponseDto;
import com.crop.goodcrop.domain.review.entity.Review;
import com.crop.goodcrop.domain.review.service.ReviewService;
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
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long memberId,
            @PathVariable Long productId,
            @RequestBody ReviewRequestDto reviewRequestDto
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
}
