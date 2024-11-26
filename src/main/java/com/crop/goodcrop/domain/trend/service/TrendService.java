package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.domain.trend.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrendService {
    private final TrendRepository trendRepository;
}
