package com.crop.goodcrop.domain.member.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class MemberUpdateRequestDto {

    private Long id;

    @Size(min = 8, max = 60, message = "비밀번호는 8자 이상, 60자 이하로 설정해야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$", message = "비밀번호는 최소 8자, 하나 이상의 문자, 숫자 및 특수문자를 포함해야 합니다.")
    private String password;

    @Nullable
    @Length(min = 1, max = 10, message = "닉네임은 최소 1자 최대 10자로 입력해야 합니다")
    private String nickname;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

}
