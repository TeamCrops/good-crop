package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.domain.trend.dto.TrendResponseDto;
import com.crop.goodcrop.domain.trend.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendService {
    private final TrendRepository trendRepository;

    public List<TrendResponseDto> retrieveTrend() {
        return trendRepository.findTopFiveOrderBySearchCount();
    }
}
