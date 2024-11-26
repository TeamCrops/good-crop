package com.crop.goodcrop.member;

import com.crop.goodcrop.domain.member.dto.request.MemberRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.domain.member.service.MemberService;
import com.crop.goodcrop.domain.review.dto.response.ReviewResponseDto;
import com.crop.goodcrop.domain.review.entity.Review;
import com.crop.goodcrop.domain.review.service.ReviewService;
import com.crop.goodcrop.security.entity.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    @DisplayName("프로필 조회 - 성공")
    void receiveUserInfo_Success() {
        // Given
        Member member = memberRepository.save(Member.builder()
                .id(1L)
                .email("sparta@email.com")
                .nickname("sparta")
                .password("password1234!")
                .birth(LocalDate.parse("2024-11-26"))
                .build());

        UserDetailsImpl userDetails = new UserDetailsImpl(member);

        // When
        MemberRequestDto requestDto = MemberRequestDto.builder()
                .id(member.getId())
                .build();

        MemberResponseDto responseDto = memberService.receiveUserInfo(requestDto, userDetails);

        // Then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(member.getId());
        assertThat(responseDto.getNickname()).isEqualTo(member.getNickname());
        assertThat(responseDto.getBirth()).isEqualTo(member.getBirth().toString());

    }

    @Test
    @DisplayName("프로필 수정 - 성공")
    void modifyUserInfo_Success() {
    }

    @Test
    @DisplayName("회원 탈퇴 - 성공")
    void deleteUser_Success() {
    }
}