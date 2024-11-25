package com.crop.goodcrop.domain.product.service;

import com.crop.goodcrop.domain.product.dto.response.ProductResponseDto;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.domain.review.repository.ReviewRepository;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductResponseDto retrieveProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseException(ErrorCode.PRODUCT_NOT_FOUND));
        Double avgScore = Optional.ofNullable(reviewRepository.findAverageScoreByProduct(product))
                .orElse(-1.0);

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                avgScore);
    }
}
