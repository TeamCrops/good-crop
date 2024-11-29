package com.crop.goodcrop.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDeleteRequestDto {

    private Long id;
    private String password;
}
