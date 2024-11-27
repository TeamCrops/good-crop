package com.crop.goodcrop.member;

import com.crop.goodcrop.domain.member.dto.request.MemberRequestDto;
import com.crop.goodcrop.domain.member.dto.request.MemberUpdateRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberResponseDto;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MemberRetrieveServiceTest {

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

    }

    @Test
    @DisplayName("프로필 조회 - 성공")
    void receiveUserInfo_Success() {
        // Given
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .id(1L)
                .build();

        // When
        MemberResponseDto responseDto = memberService.receiveUserInfo(requestDto, userDetails);

        // Then
        assertEquals(member.getId(), responseDto.getId());
        assertEquals(member.getNickname(), responseDto.getNickname());
        assertEquals(member.getBirth(), responseDto.getBirth());

    }

    @Test
    @DisplayName("프로필 조회 - 실패(ID 불일치)")
    void receiveUserInfo_IdFail() {
        // Given
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .id(member.getId()+1L)
                .build();
        // When
        // Then
        ResponseException exception =  assertThrows(ResponseException.class, () -> {
            memberService.receiveUserInfo(requestDto, userDetails);
        }, "ID가 일치하지 않아야 합니다.");

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode(), "예상된 에러코드와 일치해야 합니다.");

    }

    @Test
    @DisplayName("프로필 조회 - 실패 (사용자 없음)")
    void receiveUserInfo_UserNotFound_Fail() {

        // Given
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .id(999L) // 존재하지 않는 ID
                .build();
        // When
        // Then
        ResponseException exception = assertThrows(ResponseException.class, () -> {
            memberService.receiveUserInfo(requestDto, userDetails);
        }, "존재하지 않는 사용자를 조회하려고 하면 예외가 발생해야 합니다.");

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode(), "예상된 에러코드와 일치해야 합니다.");
    }

}