package com.crop.goodcrop.domain.trend.repository;

import com.crop.goodcrop.domain.trend.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrendRepository extends JpaRepository<SearchHistory, Long> {
}
