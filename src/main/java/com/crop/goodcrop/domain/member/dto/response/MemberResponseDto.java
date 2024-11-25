package com.crop.goodcrop.domain.member.dto.response;

import com.crop.goodcrop.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String nickname;
    private LocalDate birth;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemberResponseDto(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.birth = member.getBirth();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
    }
}
