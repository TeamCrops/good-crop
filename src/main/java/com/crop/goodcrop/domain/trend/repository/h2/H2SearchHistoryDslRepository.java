package com.crop.goodcrop.domain.trend.repository.h2;

import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;

import java.util.List;

public interface H2SearchHistoryDslRepository {
    List<TopKeywordDto> findTopKeywordOrderBySearchCount();
}
