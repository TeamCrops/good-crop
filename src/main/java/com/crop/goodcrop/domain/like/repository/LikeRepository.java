package com.crop.goodcrop.domain.like.repository;

import com.crop.goodcrop.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByProductId(Long productId);

    Like findByProductIdAndMemberId(Long productId, Long memberId);
}
