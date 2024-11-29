package com.crop.goodcrop.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {
    @NotBlank(message = "이메일 입력은 필수입니다")
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "올바른 이메일 형식을 입력하세요.")
    private String email;

    @Size(min = 8, max = 60, message = "비밀번호는 8자 이상, 60자 이하로 설정해야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$", message = "비밀번호는 최소 8자, 하나 이상의 문자, 숫자 및 특수문자를 포함해야 합니다.")
    @NotBlank(message = "비밀번호 입력은 필수입니다")
    private String password;
}
