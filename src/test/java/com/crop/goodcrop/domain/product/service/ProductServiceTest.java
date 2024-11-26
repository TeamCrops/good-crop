package com.crop.goodcrop.domain.product.service;

import com.crop.goodcrop.domain.product.dto.response.ProductResponseDto;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.domain.review.repository.ReviewRepository;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ProductService productService;



    @Test
    @DisplayName("상품 단건 조회 성공 시")
    public void retrieveSuccessTest1 () {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .name("Sample001")
                .price(10000)
                .build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Double avgScore = 4.2;
        when(reviewRepository.findAverageScoreByProduct(product)).thenReturn(avgScore);

        // when
        ProductResponseDto respDto = productService.retrieveProduct(productId);

        // then
        assertThat(respDto.getId()).isEqualTo(1L);
        assertThat(respDto.getName()).isEqualTo("Sample001");
        assertThat(respDto.getPrice()).isEqualTo(10000);
        assertThat(respDto.getAvgScore()).isEqualTo(4.2);
    }

    @Test
    @DisplayName("상품 단건 조회 실패 시 404 예외 발생")
    public void retrieveFailTest1 () {
        // given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.retrieveProduct(productId))
                .isInstanceOf(ResponseException.class)
                .hasMessage("존재하지 않는 상품입니다")
                .extracting("ErrorCode")
                .isEqualTo(ErrorCode.PRODUCT_NOT_FOUND)
                .extracting("httpStatus")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}