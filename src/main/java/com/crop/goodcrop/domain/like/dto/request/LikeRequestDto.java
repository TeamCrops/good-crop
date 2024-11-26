package com.crop.goodcrop.domain.like.dto.request;

import com.crop.goodcrop.domain.like.entity.Like;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeRequestDto {
    private Long memberId;

    public Like convertDtoToEntity(Member member, Product product) {
        return Like.builder()
                .member(member)
                .product(product)
                .build();

    }
}
