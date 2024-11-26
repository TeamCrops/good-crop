package com.crop.goodcrop.domain.product.repository;

import com.crop.goodcrop.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryQuery {

    Optional<Page<Product>> searchProductsWithFilters(String keyword, int minPrice, boolean isTrend, Pageable pageable);
}
