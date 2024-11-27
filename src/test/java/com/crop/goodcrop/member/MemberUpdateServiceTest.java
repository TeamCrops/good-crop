package com.crop.goodcrop.member;

import com.crop.goodcrop.domain.member.dto.request.MemberUpdateRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberUpdateResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.member.service.MemberService;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import com.crop.goodcrop.security.entity.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MemberUpdateServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @Mock
    private UserDetailsImpl userDetails;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member = Member.builder()
                .id(1L)
                .email("test@email.com")
                .nickname("testUser")
                .password("password1234!")
                .birth(LocalDate.of(2000, 1, 1))
                .build();

        userDetails = new UserDetailsImpl(member);
        // memberRepository의 findById 동작 설정 (Mockito 사용)
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

    }

    @Test
    @Transactional
    @DisplayName("프로필 수정 - 성공")
    void modifyUserInfo_Success() {

        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .id(userDetails.getUser().getId())
                .password("password1234!")
                .nickname("newsparta")
                .birth(LocalDate.of(2000, 1, 1))
                .build();

        when(memberRepository.findById(requestDto.getId())).thenReturn(Optional.of(member));
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");  // 비밀번호 인코딩


        MemberUpdateResponseDto responseDto = memberService.modifyUserInfo(requestDto, userDetails);
        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getNickname()).isEqualTo("newsparta");
        assertThat(responseDto.getBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    @DisplayName("프로필 수정 - 실패 (사용자 권한 없음)")
    void modifyUserInfo_Forbidden_Fail() {

        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .id(2L) // ID 불일치
                .password("newPassword!123")
                .nickname("newNickname")
                .birth(LocalDate.of(1995, 5, 20))
                .build();

        // When & Then
        ResponseException exception = assertThrows(ResponseException.class, () -> {
            memberService.modifyUserInfo(requestDto, userDetails);
        });

        assertEquals(ErrorCode.USER_UPDATE_FORBIDDEN, exception.getErrorCode());

    }

}
