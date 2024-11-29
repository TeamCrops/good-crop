package com.crop.goodcrop.domain.member.controller;

import com.crop.goodcrop.domain.member.dto.request.MemberDeleteRequestDto;
import com.crop.goodcrop.domain.member.dto.request.MemberRequestDto;
import com.crop.goodcrop.domain.member.dto.request.MemberUpdateRequestDto;
import com.crop.goodcrop.domain.member.dto.response.MemberResponseDto;
import com.crop.goodcrop.domain.member.dto.response.MemberUpdateResponseDto;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.member.service.MemberService;
import com.crop.goodcrop.security.entity.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/user/profile")
    public ResponseEntity<MemberResponseDto> retrieveProfile(@RequestBody MemberRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MemberResponseDto respDto = memberService.receiveUserInfo(requestDto, userDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(respDto);
    }

    @PutMapping("/user/profile")
    public ResponseEntity<MemberUpdateResponseDto> updateProfile(@Valid @RequestBody MemberUpdateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        MemberUpdateResponseDto respDto = memberService.modifyUserInfo(requestDto, userDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(respDto);
    }

    @DeleteMapping("/user/profile")
    public ResponseEntity<Void> deleteProfile(@Valid @RequestBody MemberDeleteRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        memberService.deleteUser(requestDto, userDetails);
        return ResponseEntity.noContent().build();
    }

}
