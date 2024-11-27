package com.crop.goodcrop.domain.trend.repository;

import com.crop.goodcrop.domain.trend.entity.TopKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopKeywordRepository extends JpaRepository<TopKeyword, Long> {
    boolean existsByKeyword(String keyword);
}
