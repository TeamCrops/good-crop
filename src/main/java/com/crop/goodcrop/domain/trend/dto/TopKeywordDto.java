package com.crop.goodcrop.domain.trend.dto;

import com.crop.goodcrop.domain.trend.entity.TopKeyword;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class TopKeywordDto {
    private String keyword;
    private Long count;

    @QueryProjection
    public TopKeywordDto(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    public static TopKeywordDto from(TopKeyword topKeyword) {
        TopKeywordDto topKeywordDto = new TopKeywordDto();
        topKeywordDto.keyword = topKeyword.getKeyword();
        topKeywordDto.count = topKeyword.getCount();
        return topKeywordDto;
    }

    public TopKeyword convertDtoToEntity() {
        return TopKeyword.builder()
                .keyword(keyword)
                .count(count)
                .build();
    }
}
