package com.example.notion.domain.user.dto.response;

public record TokenResponse(
        String token,      // JWT 토큰
        String email,      // 사용자 식별용 이메일
        String name        // 사용자 이름 (선택적)
) {}
