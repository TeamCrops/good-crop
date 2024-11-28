package com.crop.goodcrop.domain.product.service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class ProductCacheTest {
    @Autowired
    private ProductService productService;

    // 캐시 적용(사용자검색어 = 인기검색어)
    @Test
    public void testSearchProductsWithCache() {
        String keyword = "이름578473";
        int minPrice = 0;
        boolean isTrend = false;
        boolean existWord = true;
        int page = 1;
        int size = 10;
        // 첫 번째 호출 (캐시 미스 발생)
        System.out.println("캐시 미스 발생 - 첫 번째 호출");
        productService.searchProductsWithCache(keyword, minPrice, isTrend, existWord, page, size);
        // 두 번째 호출 (캐시 히트 발생)
        System.out.println("캐시 히트 발생 - 두 번째 호출");
        productService.searchProductsWithCache(keyword, minPrice, isTrend, existWord, page, size);
    }

    // 캐시 미적용(사용자검색어 != 인기검색어)
    @Test
    public void testSearchProductsWithNoCache() {
        String keyword = "Product A";
        int minPrice = 0;
        boolean isTrend = false;
        boolean existWord = false;
        int page = 1;
        int size = 10;
        // 첫 번째 호출 (캐시 미스 발생)
        System.out.println("캐시 미스 발생 - 첫 번째 호출");
        productService.searchProductsWithCache(keyword, minPrice, isTrend, existWord, page, size);
        // 두 번째 호출 (캐시 히트 발생)
        System.out.println("캐시 히트 발생 - 두 번째 호출");
        productService.searchProductsWithCache(keyword, minPrice, isTrend, existWord, page, size);
    }
}