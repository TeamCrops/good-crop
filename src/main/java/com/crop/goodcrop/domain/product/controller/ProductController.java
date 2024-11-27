package com.crop.goodcrop.domain.product.controller;


import com.crop.goodcrop.domain.common.dto.PageResponseDto;
import com.crop.goodcrop.domain.product.dto.response.ProductResponseDto;
import com.crop.goodcrop.domain.product.service.ProductService;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import com.crop.goodcrop.security.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> retrieveProduct(@PathVariable Long productId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.retrieveProduct(productId));
    }

    @GetMapping("v1/products")
    public ResponseEntity<PageResponseDto<ProductResponseDto>> searchProducts(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0", required = false) int minPrice,
            @RequestParam(defaultValue = "false") boolean isTrend,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Long memberId = (userDetails != null) ? userDetails.getUser().getId() : -1;
        System.out.println("memberId: " + memberId);

        // keyword가 공백 또는 빈 문자열인 경우 예외 처리
        if (keyword.trim().isEmpty()) {
            throw new ResponseException(ErrorCode.BAD_INPUT, "키워드가 공백이거나 비어있을 수 없습니다.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.searchProducts(keyword, minPrice, isTrend, page, size));
    }

}
