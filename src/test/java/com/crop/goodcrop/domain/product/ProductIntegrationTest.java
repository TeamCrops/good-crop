package com.crop.goodcrop.domain.product;

import com.crop.goodcrop.config.QueryDslConfig;
import com.crop.goodcrop.domain.common.dto.PageResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.product.dto.response.ProductResponseDto;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.domain.product.service.ProductService;
import com.crop.goodcrop.domain.review.entity.Review;
import com.crop.goodcrop.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(value = {QueryDslConfig.class, ProductService.class})
public class ProductIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        // 공통 데이터 설정
        Product savedProduct1 = productRepository.save(Product.builder()
                .name("MockProduct1")
                .price(2000)
                .build());

        Product savedProduct2 = productRepository.save(Product.builder()
                .name("MockProduct2")
                .price(1000)
                .build());

        Product savedProduct3 = productRepository.save(Product.builder()
                .name("MockProduct3")
                .price(400)
                .build());

        Member savedMember = memberRepository.save(Member.builder()
                .email("sparta@email.com")
                .nickname("sparta")
                .password("password1234!")
                .birth(LocalDate.parse("2024-11-26"))
                .build());

        Member savedMember2 = memberRepository.save(Member.builder()
                .email("sparta2@email.com")
                .nickname("sparta2")
                .password("password1234!")
                .birth(LocalDate.parse("2024-11-26"))
                .build());

        reviewRepository.save(Review.builder()
                .product(savedProduct2) // MockProduct2에 연결된 리뷰
                .member(savedMember)
                .score(4)
                .build());

        reviewRepository.save(Review.builder()
                .product(savedProduct2) // MockProduct2에 연결된 리뷰
                .member(savedMember2)
                .score(5)
                .build());

    }

    @Test
    @DisplayName("상품 검색 시 minPrice가 0원일 때 상품명 오름차순 테스트")
    public void searchSuccessTest1() {
        // given
        String keyword = "Product";
        int minPrice = 0;
        boolean isTrend = false;
        int page = 1;
        int size = 10;

        // when
        PageResponseDto<ProductResponseDto> result = productService.searchProducts(keyword, minPrice, isTrend, page, size);

        // then
        assertThat(result.getData().size()).isEqualTo(3);
        assertThat(result.getData().get(0).getName()).isEqualTo("MockProduct1");
        assertThat(result.getData().get(0).getAvgScore()).isEqualTo(-1.0);
    }

    @Test
    @DisplayName("상품 검색 시 minPrice가 0원 이상일 때 가격 내림차순 테스트")
    public void searchSuccessTest2() {
        // given
        String keyword = "Product";
        int minPrice = 500;
        boolean isTrend = false;
        int page = 1;
        int size = 10;

        // when
        PageResponseDto<ProductResponseDto> result = productService.searchProducts(keyword, minPrice, isTrend, page, size);

        // then
        assertThat(result.getData().size()).isEqualTo(2);
        assertThat(result.getData().get(0).getName()).isEqualTo("MockProduct2");
        assertThat(result.getData().get(0).getAvgScore()).isEqualTo(4.5);

        assertThat(result.getData().get(1).getName()).isEqualTo("MockProduct1");
        assertThat(result.getData().get(1).getAvgScore()).isEqualTo(-1.0);
    }
}
