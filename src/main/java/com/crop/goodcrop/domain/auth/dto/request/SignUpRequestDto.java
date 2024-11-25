package com.crop.goodcrop.domain.auth.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message = "이메일 입력은 필수입니다")
    @Email
    private String email;
    @NotBlank(message = "비밀번호 입력은 필수입니다")
    private String password;
    @NotBlank(message = "닉네임 입력은 필수입니다")
    private String nickname;
    @Nullable
    private LocalDate birth;

}
