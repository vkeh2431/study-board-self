package com.example.studyboardself.global.exception;

import java.util.Map;

public record ErrorResponse(
        int status,
        String code,
        String message,
        Map<String, String> fieldErrors
) {
    public ErrorResponse(int stauts, String code, String message) {
        this(stauts, code, message, null);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getCode(), errorCode.getDefaultMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getCode(), message);
    }

    public static ErrorResponse of(ErrorCode errorCode, Map<String, String> fieldErrors) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getCode(), errorCode.getDefaultMessage(), fieldErrors);
    }
}
