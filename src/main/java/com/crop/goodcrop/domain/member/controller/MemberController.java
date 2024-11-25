package com.crop.goodcrop.domain.member.controller;

import com.crop.goodcrop.domain.member.dto.request.MemberRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberResponseDto;
import com.crop.goodcrop.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/user/profile")
    public ResponseEntity<MemberResponseDto> profile(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto respDto = memberService.receiveUserInfo(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(respDto);
    }

}
