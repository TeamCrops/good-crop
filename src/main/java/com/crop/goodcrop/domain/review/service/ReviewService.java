package com.crop.goodcrop.domain.review.service;

import com.crop.goodcrop.domain.common.dto.PageResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.domain.review.dto.request.ReviewCreateRequestDto;
import com.crop.goodcrop.domain.review.dto.request.ReviewModifyRequestDto;
import com.crop.goodcrop.domain.review.dto.response.ReviewResponseDto;
import com.crop.goodcrop.domain.review.entity.Review;
import com.crop.goodcrop.domain.review.repository.ReviewRepository;
import com.crop.goodcrop.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.crop.goodcrop.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ReviewResponseDto createReview(Long memberId, Long productId, ReviewCreateRequestDto reviewRequestDto) {
        // DB에 존재하는 상품인지 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseException(PRODUCT_NOT_FOUND));

        Optional<Member> member = memberRepository.findById(memberId);

        Review review = reviewRepository.save(Review.of(reviewRequestDto.getComment(), reviewRequestDto.getScore(), member.orElse(null), product));

        return ReviewResponseDto.from(review);
    }

    @Transactional(readOnly = true)
    public PageResponseDto<ReviewResponseDto> retrieveReviews(Long productId, int page) {
        // DB에 존재하는 상품인지 확인
        productRepository.findById(productId)
                .orElseThrow(() -> new ResponseException(PRODUCT_NOT_FOUND));

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "createdAt");

        Page<Review> reviews = reviewRepository.findByProductId(productId, pageable);
        Page<ReviewResponseDto> responses = reviews.map(ReviewResponseDto::from);
        return PageResponseDto.of(responses.toList(), pageable, responses.getTotalPages());
    }

    @Transactional
    public ReviewResponseDto modifyReview(Long memberId, Long productId, Long reviewId, ReviewModifyRequestDto requestDto) {
        // DB에 존재하는 상품인지 확인
        productRepository.findById(productId)
                .orElseThrow(() -> new ResponseException(PRODUCT_NOT_FOUND));
        // DB에 존재하는 리뷰인지 확인
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseException(REVIEW_NOT_FOUND));

        // 리뷰를 수정할 권한이 없는 경우
        if (!memberId.equals(review.getMember().getId())) {
            throw new ResponseException(REVIEW_NO_PERMISSION_TO_EDIT);
        }
        // 해당 상품의 리뷰가 아닌 경우
        if (!productId.equals(review.getProduct().getId())) {
            throw new ResponseException(REVIEW_NOT_OWNED);
        }

        review.modify(requestDto.getComment(), requestDto.getScore());

        return ReviewResponseDto.from(review);
    }

    @Transactional
    public void deleteReview(Long memberId, Long productId, Long reviewId) {
        // DB에 존재하는 상품인지 확인
        productRepository.findById(productId)
                .orElseThrow(() -> new ResponseException(PRODUCT_NOT_FOUND));
        // DB에 존재하는 리뷰인지 확인
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseException(REVIEW_NOT_FOUND));

        // 리뷰를 수정할 권한이 없는 경우
        if (!memberId.equals(review.getMember().getId())) {
            throw new ResponseException(REVIEW_NO_PERMISSION_TO_EDIT);
        }
        // 해당 상품의 리뷰가 아닌 경우
        if (!productId.equals(review.getProduct().getId())) {
            throw new ResponseException(REVIEW_NOT_OWNED);
        }

        reviewRepository.delete(review);
    }
}
