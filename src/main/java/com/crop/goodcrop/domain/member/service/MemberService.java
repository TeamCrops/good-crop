package com.crop.goodcrop.domain.member.service;

import com.crop.goodcrop.domain.member.dto.request.MemberDeleteRequestDto;
import com.crop.goodcrop.domain.member.dto.request.MemberRequestDto;
import com.crop.goodcrop.domain.member.dto.request.MemberUpdateRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberResponseDto;
import com.crop.goodcrop.domain.member.dto.response.MemberUpdateResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto receiveUserInfo(MemberRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getId())
                .orElseThrow(()-> new ResponseException(ErrorCode.USER_NOT_FOUND));

        return new MemberResponseDto(member);
    }

    @Transactional
    public MemberUpdateResponseDto modifyUserInfo(MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getId())
                .orElseThrow(()-> new ResponseException(ErrorCode.USER_NOT_FOUND));

        member.modify(passwordEncoder.encode(requestDto.getPassword()), requestDto.getNickname(), requestDto.getBirth());
        return new MemberUpdateResponseDto(member);
    }

    public void deleteUser(MemberDeleteRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getId())
                .orElseThrow(()-> new ResponseException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new ResponseException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        memberRepository.delete(member);
    }
}
