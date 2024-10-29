package com.example.notion.global.exception;

// global/exception/ErrorResponse.java
public record ErrorResponse(
        String message,
        String code,
        int status
) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getMessage(),
                errorCode.getCode(),
                errorCode.getStatus()
        );
    }
}
