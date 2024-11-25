package com.crop.goodcrop.domain.product.controller;


import com.crop.goodcrop.domain.product.dto.response.ProductResponseDto;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
