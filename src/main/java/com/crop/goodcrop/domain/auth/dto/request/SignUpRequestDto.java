package com.crop.goodcrop.domain.auth.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message = "이메일 입력은 필수입니다")
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "올바른 이메일 형식을 입력하세요.")
    private String email;

    @Size(min = 8, max = 60, message = "비밀번호는 8자 이상, 60자 이하로 설정해야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$", message = "비밀번호는 최소 8자, 하나 이상의 문자, 숫자 및 특수문자를 포함해야 합니다.")    @NotBlank(message = "비밀번호 입력은 필수입니다")
    private String password;

    @Length(min = 1, max = 10, message = "닉네임은 최소 1자 최대 10자로 입력해야 합니다")
    @NotBlank(message = "닉네임 입력은 필수입니다")
    private String nickname;

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

}
