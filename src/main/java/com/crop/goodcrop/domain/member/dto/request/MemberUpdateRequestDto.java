package com.crop.goodcrop.domain.member.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberUpdateRequestDto {

    private Long id;
    private String password;
    private String nickname;
    private LocalDate birth;

}
