package com.crop.goodcrop.domain.auth.controller;

import com.crop.goodcrop.domain.auth.dto.request.SignUpRequestDto;
import com.crop.goodcrop.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    //가입
    @PostMapping("/signup")
    public ResponseEntity<Void>signup(@Valid @RequestBody SignUpRequestDto requestDto) {
        authService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
