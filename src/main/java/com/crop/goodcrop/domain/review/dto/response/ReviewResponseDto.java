package com.crop.goodcrop.domain.review.dto.response;

import com.crop.goodcrop.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private int score;
    private String comment;
    private String nickname;
    private LocalDateTime createdAt;

    public static ReviewResponseDto from(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getScore(),
                review.getComment(),
                review.getMember().getNickname(),
                review.getCreatedAt()
        );
    }
}
