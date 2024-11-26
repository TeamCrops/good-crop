package com.crop.goodcrop.domain.review.repository;

import com.crop.goodcrop.domain.product.dto.response.ProductAvgScoreDto;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByProductId(Long productId, Pageable pageable);

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.product = :product")
    Double findAverageScoreByProduct(Product product);

    @Query("SELECT new com.crop.goodcrop.domain.product.dto.response.ProductAvgScoreDto(p, AVG(r.score))" +
            "FROM Product p LEFT JOIN FETCH Review r ON p.id = r.product.id WHERE p IN :products GROUP BY p")
    List<ProductAvgScoreDto> findProductWithAvgScores(@Param("products") List<Product> products);
}
