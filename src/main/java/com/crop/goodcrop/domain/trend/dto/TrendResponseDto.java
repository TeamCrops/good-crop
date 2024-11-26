package com.crop.goodcrop.domain.trend.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
public class TrendResponseDto {
    private String keyword;
    private Long count;
    private LocalDateTime lastedAt;

    @QueryProjection
    public TrendResponseDto(String keyword, Long count, LocalDateTime lastedAt) {
        this.keyword = keyword;
        this.count = count;
        this.lastedAt = lastedAt;
    }
}
