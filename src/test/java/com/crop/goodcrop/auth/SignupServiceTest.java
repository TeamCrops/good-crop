package com.crop.goodcrop.auth;

import com.crop.goodcrop.domain.auth.dto.request.SignUpRequestDto;
import com.crop.goodcrop.domain.auth.service.AuthService;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.exception.ResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SignupServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private SignUpRequestDto signupRequestDto;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        //회원가입 dto
        signupRequestDto = SignUpRequestDto.builder()
                .email("test1@email.com")
                .password("password123!")
                .nickname("구황")
                .birth(LocalDate.of(2024,11,25))
                .build();
    }

    @Test
    @DisplayName("회원가입_성공")
    public void testSignup_Success(){
        //given : 이메일 중복 없음
        when(memberRepository.existsByEmail(signupRequestDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signupRequestDto.getPassword())).thenReturn("encodingPassword");

        //when
        authService.signup(signupRequestDto);

        //then : save메서드 호출 확인, Member 객체 인자 받았는지 확인
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("회원가입_실패_중복이메일")
    public void testSignup_Fail(){
        //given : 이메일 중복
        when(memberRepository.existsByEmail(signupRequestDto.getEmail())).thenReturn(true);

        // when & then : 예외가 발생하는지 검증
        assertThrows(ResponseException.class, () -> {
            authService.signup(signupRequestDto);
        });

        // save 호출되지 않았는지 확인
        verify(memberRepository, never()).save(any(Member.class));
    }

}
