package com.crop.goodcrop.member;

import com.crop.goodcrop.domain.member.dto.request.MemberDeleteRequestDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.member.service.MemberService;
import com.crop.goodcrop.security.entity.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class MemberDeleteServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private MemberDeleteRequestDto memberDeleteRequestDto;
    private Member member;


    @Mock
    private UserDetailsImpl userDetailsImpl;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        memberDeleteRequestDto = MemberDeleteRequestDto.builder()
                .id(1L)
                .password("password123!")
                .build();


        member = Member.builder()
                .id(1L)
                .email("sparta@email.com")
                .nickname("sparta")
                .password("password123!")
                .birth(LocalDate.parse("2024-11-26"))
                .build();

        // UserDetailsImpl 모킹
        when(userDetailsImpl.getUser()).thenReturn(member);
    }


    @Test
    @DisplayName("회원탈퇴_성공")
    public void testDeleteUser_Success() {
        // given
        when(memberRepository.existsByEmail(member.getEmail())).thenReturn(true);
        when(passwordEncoder.matches(memberDeleteRequestDto.getPassword(), member.getPassword())).thenReturn(true);

        // when
        memberService.deleteUser(memberDeleteRequestDto, userDetailsImpl);

        // then
        verify(memberRepository, times(1)).delete(member);
    }
}
