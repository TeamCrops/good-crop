package com.crop.goodcrop.domain.trend.repository.mysql;

import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;

import java.util.List;

public interface SearchHistoryDslRepository {
    List<TopKeywordDto> findTopFiveOrderBySearchCount();
}
