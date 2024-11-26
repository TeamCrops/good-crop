package com.crop.goodcrop.domain.member.controller;

import com.crop.goodcrop.domain.member.dto.request.MemberDeleteRequestDto;
import com.crop.goodcrop.domain.member.dto.request.MemberRequestDto;
import com.crop.goodcrop.domain.member.dto.request.MemberUpdateRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberResponseDto;
import com.crop.goodcrop.domain.member.dto.response.MemberUpdateResponseDto;
import com.crop.goodcrop.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/user/profile")
    public ResponseEntity<MemberResponseDto> retrieveProfile(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto respDto = memberService.receiveUserInfo(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(respDto);
    }

    @PutMapping("/user/profile")
    public ResponseEntity<MemberUpdateResponseDto> updateProfile(@Valid @RequestBody MemberUpdateRequestDto requestDto){
        MemberUpdateResponseDto respDto = memberService.modifyUserInfo(requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(respDto);
    }

    @DeleteMapping("/user/profile")
    public ResponseEntity<Void> deleteProfile(@Valid @RequestBody MemberDeleteRequestDto requestDto){
        memberService.deleteUser(requestDto);
        return ResponseEntity.noContent().build();
    }

}
