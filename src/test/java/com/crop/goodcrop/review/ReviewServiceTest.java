package com.crop.goodcrop.review;

import com.crop.goodcrop.domain.common.dto.PageResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.domain.review.dto.request.ReviewRequestDto;
import com.crop.goodcrop.domain.review.dto.response.ReviewResponseDto;
import com.crop.goodcrop.domain.review.entity.Review;
import com.crop.goodcrop.domain.review.repository.ReviewRepository;
import com.crop.goodcrop.domain.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ReviewServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewService(memberRepository, productRepository, reviewRepository);
    }

    @Test
    @DisplayName("리뷰 생성 - 성공")
    void createReview_Success() {
        // Given
        Member member = entityManager.persistAndFlush(Member.builder()
                .email("sparta@email.com")
                .password("password1234!")
                .build());

        Product product = entityManager.persistAndFlush(Product.builder()
                .name("크리스마스 한정판 귤")
                .price(333333)
                .build());

        ReviewRequestDto requestDto = ReviewRequestDto.builder()
                .score(5)
                .comment("꿀맛!!")
                .build();

        // When
        ReviewResponseDto responseDto = reviewService.createReview(member.getId(), product.getId(), requestDto);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getScore()).isEqualTo(5);
        assertThat(responseDto.getComment()).isEqualTo("꿀맛!!");
        assertThat(responseDto.getNickname()).isEqualTo(member.getNickname());

        Review savedReview = entityManager.find(Review.class, responseDto.getId());
        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getMember()).isEqualTo(member);
        assertThat(savedReview.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("리뷰 조회 - 성공")
    void retrieveReview_Success() {
        // Given
        Member member = entityManager.persistAndFlush(
                Member.builder()
                        .email("sparta@email.com")
                        .password("password1234!")
                        .build());

        Product product = entityManager.persistAndFlush(
                Product.builder()
                        .name("크리스마스 한정판 귤")
                        .price(333333)
                        .build());

        for (int i = 1; i <= 15; i++) {
            entityManager.persistAndFlush(
                    Review.builder()
                            .member(member)
                            .product(product)
                            .comment("Review " + i)
                            .score(5)
                            .build());
        }

        // When
        PageResponseDto<Review> responses = reviewService.retrieveReviews(product.getId(), 1);

        // Then
        assertThat(responses).isNotNull();
        assertThat(responses.getSize()).isEqualTo(10); // 페이지 크기가 10인지 확인
        assertThat(responses.getTotalPage()).isEqualTo(2); // 총 페이지 개수 확인
        assertThat(responses.getData().get(0).getComment()).isEqualTo("Review 15"); // 첫 번째 리뷰 내용 확인
        assertThat(responses.getData().get(9).getComment()).isEqualTo("Review 6"); // 마지막 리뷰 내용 확인
    }
}
