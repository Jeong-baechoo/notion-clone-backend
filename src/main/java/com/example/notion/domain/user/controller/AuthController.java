package com.example.notion.domain.user.controller;

import com.example.notion.domain.user.dto.request.LoginRequest;
import com.example.notion.domain.user.dto.request.SignupRequest;
import com.example.notion.domain.user.dto.response.TokenResponse;
import com.example.notion.domain.user.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        TokenResponse tokenResponse = userAuthService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request) {
        userAuthService.signup(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        userAuthService.logout(token);
        return ResponseEntity.ok().build();
    }



}
