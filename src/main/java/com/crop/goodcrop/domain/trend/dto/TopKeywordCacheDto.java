package com.crop.goodcrop.domain.trend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
public class TopKeywordCacheDto {
    private List<TopKeywordDto> topKeywords;

    public TopKeywordCacheDto(List<TopKeywordDto> topKeywords) {
        this.topKeywords = topKeywords;
    }
}
