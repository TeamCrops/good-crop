package com.crop.goodcrop.domain.review.repository;

import com.crop.goodcrop.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
