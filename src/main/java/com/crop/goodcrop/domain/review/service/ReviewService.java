package com.crop.goodcrop.domain.review.service;

import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.domain.review.dto.request.ReviewRequestDto;
import com.crop.goodcrop.domain.review.dto.response.ReviewResponseDto;
import com.crop.goodcrop.domain.review.entity.Review;
import com.crop.goodcrop.domain.review.repository.ReviewRepository;
import com.crop.goodcrop.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.crop.goodcrop.exception.ErrorCode.PRODUCT_NOT_FOUND;
import static com.crop.goodcrop.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ReviewResponseDto createReview(Long memberId, Long productId, ReviewRequestDto reviewRequestDto) {
        // DB에 존재하는 유저인지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseException(USER_NOT_FOUND));

        // DB에 존재하는 상품인지 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseException(PRODUCT_NOT_FOUND));

        Review review = reviewRepository.save(Review.of(reviewRequestDto.getComment(), reviewRequestDto.getScore(), member, product));

        return ReviewResponseDto.from(review);
    }
}
