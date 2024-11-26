package com.crop.goodcrop.domain.trend.repository;

import com.crop.goodcrop.domain.trend.dto.TrendResponseDto;

import java.util.List;

public interface TrendDslRepository {
    List<TrendResponseDto> findTopFiveOrderBySearchCount();
}
