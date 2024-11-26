package com.crop.goodcrop.domain.review.repository;

import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByProductId(Long productId, Pageable pageable);

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.product = :product")
    Double findAverageScoreByProduct(Product product);
}
