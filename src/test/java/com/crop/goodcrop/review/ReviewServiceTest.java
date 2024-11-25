package com.crop.goodcrop.review;

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
}
