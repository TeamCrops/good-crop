package com.crop.goodcrop.domain.product.service;

import com.crop.goodcrop.domain.common.dto.PageResponseDto;
import com.crop.goodcrop.domain.product.dto.response.ProductAvgScoreDto;
import com.crop.goodcrop.domain.product.dto.response.ProductResponseDto;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.domain.review.repository.ReviewRepository;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public PageResponseDto<ProductResponseDto> searchProducts(String keyword, int minPrice, boolean isTrend, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Product> products = productRepository.searchProductsWithFilters(keyword, minPrice, isTrend, pageable)
                .orElseThrow(() -> new ResponseException(ErrorCode.PRODUCT_SEARCH_NOT_FOUND));

        /*
        성능 문제 발생 예상
        List<Product> -> List<ProductResponseDto> 의 과정에서 avgScore 필요합니다.
        1. ProductAvgScoreDto 로 Product, AvgScore 를 review 한번 더 LEFT JOIN 하여 조회
        2. Stream API 로 List<ProductResponseDto> 생성
        3. List<ProductResponseDto> 를 PageResponseDto 에 담아 반환
        최대한 N+1 발생 하지 않기 위해 위 방법을 택했습니다.
         */
        List<ProductAvgScoreDto> productResponse = reviewRepository.findProductWithAvgScores(products.getContent());

        // findProductWithAvgScores 메서드는 수행 시 id 필드의 기준으로 기본 오름차순 정렬을 하기 때문에 다시 재정렬 해줘야 함
        Map<Long, ProductAvgScoreDto> productResponseMap = productResponse.stream()
                .collect(Collectors.toMap(
                        response -> response.getProduct().getId(),
                        response -> response
                ));
        List<ProductResponseDto> respDtos = products.getContent().stream()
                .map(product -> {
                    ProductAvgScoreDto avgScoreDto = productResponseMap.get(product.getId());
                    Double avgScore = (avgScoreDto != null && avgScoreDto.getAvgScore() != null) ? avgScoreDto.getAvgScore() : -1.0;
                    return new ProductResponseDto(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            avgScore
                    );
                }).toList();
        return PageResponseDto.of(
                respDtos, pageable, products.getTotalPages()
        );
    }
}
