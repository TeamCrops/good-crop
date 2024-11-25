package com.crop.goodcrop.domain.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Builder
@Getter
public class ReviewRequestDto {
    // 별점: 최소 1점, 최대 5점
    @Range(min=1, max=5)
    private int score;

    private String comment;
}
