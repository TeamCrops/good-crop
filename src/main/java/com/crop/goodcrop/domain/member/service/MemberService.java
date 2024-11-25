package com.crop.goodcrop.domain.member.service;

import com.crop.goodcrop.domain.member.dto.request.MemberRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.repository.MemberRepository;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto receiveUserInfo(MemberRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getId())
                .orElseThrow(()-> new ResponseException(ErrorCode.USER_NOT_FOUND));

        return new MemberResponseDto(member);
    }
}
