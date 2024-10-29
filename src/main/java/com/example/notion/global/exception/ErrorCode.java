package com.example.notion.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Auth
    INVALID_TOKEN(401, "AUTH_001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401, "AUTH_002", "만료된 토큰입니다."),
    ACCESS_DENIED(403, "AUTH_003", "접근 권한이 없습니다."),

    // User
    USER_NOT_FOUND(404, "USER_001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(400, "USER_002", "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(400, "USER_003", "잘못된 비밀번호입니다."),

    // Common
    INVALID_INPUT_VALUE(400, "COMMON_001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(500, "COMMON_002", "서버 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;
}
