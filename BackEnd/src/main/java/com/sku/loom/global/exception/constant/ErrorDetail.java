package com.sku.loom.global.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorDetail {
    // Token
    EMPTY_AUTHORIZATION_HEADER("T0001", HttpStatus.UNAUTHORIZED, "인증 헤더가 존재하지 않습니다."),
    EMPTY_SUBJECT("T0002", HttpStatus.UNAUTHORIZED, "JWT 토큰의 사용자 정보가 존재하지 않습니다."),
    EMPTY_TOKEN("T0003", HttpStatus.UNAUTHORIZED, "JWT 토큰의 클레임이 존재하지 않습니다."),
    EXPIRED_TOKEN("T0004", HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    INVALID_SIGNATURE("T0005", HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 서명입니다."),
    UNSUPPORTED_TOKEN("T0006", HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    INVALID_TOKEN("T0007", HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),
    INVALID_REFRESH_TOKEN("T0008", HttpStatus.BAD_REQUEST, "리프레쉬 토큰이 만료 되었거나 유효하지 않습니다."),

    // User
    NOT_FOUND_USER("U0001", HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    INVALID_VERIFICATION_CODE("U0001", HttpStatus.BAD_REQUEST, "인증코드가 올바르지 않습니다."),

    // Workspace
    NOT_FOUND_WORKSPACE("W0001", HttpStatus.NOT_FOUND, "워크스페이스를 찾을 수 없습니다."),
    WORKSPACE_OWNER_REQUIRED("W0002", HttpStatus.FORBIDDEN, "워크스페이스 소유자만 수행할 수 있는 작업입니다."),
    ALREADY_EXISTS_WORKSPACE_USER("W0003", HttpStatus.CONFLICT, "이미 워크스페이스에 참여중인 사용자입니다."),

    // Channel
    NOT_FOUND_CHANNEL("C0001", HttpStatus.NOT_FOUND, "채널을 찾을 수 없습니다."),

    // Section
    NOT_FOUND_SECTION("S0001", HttpStatus.NOT_FOUND, "섹션을 찾을 수 없습니다."),
    FORBIDDEN_SECTION("S0002", HttpStatus.FORBIDDEN, "해당 섹션에 대한 접근 권한이 없습니다"),

    // S3
    NOT_FOUND_S3_FILE("S0001", HttpStatus.NOT_FOUND, "S3에 파일이 존재하지 않습니다."),

    // Email (이메일 관련)
    EMAIL_SEND_FAILED("E0001", HttpStatus.INTERNAL_SERVER_ERROR, "이메일 발송에 실패했습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;
}
