package com.crop.goodcrop.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Builder
@Getter
public class ReviewModifyRequestDto {
    // 별점: 최소 1점, 최대 5점
    @NotBlank
    @Range(min=1, max=5)
    private int score;

    @Size(max = 100, message = "리뷰 내용은 최대 100자까지 입력 가능합니다.")
    private String comment;
}
