package com.sku.loom.domain.user.controller;

import com.sku.loom.domain.user.dto.AuthRequest;
import com.sku.loom.domain.user.dto.AuthResponse;
import com.sku.loom.domain.user.dto.EmailSendRequest;
import com.sku.loom.domain.user.dto.EmailSendResponse;
import com.sku.loom.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "회원", description = "회원관련 기능 수행")
public class UserController {

    private final UserService userService;

    @PostMapping("/email/send")
    @Operation(
            summary = "이메일 인증코드 발송",
            description = "입력된 이메일로 6자리 인증코드를 발송합니다."
    )
    @ApiResponse(responseCode = "200", description = "인증코드 발송 성공")
    public ResponseEntity<EmailSendResponse> sendEmailCode(@Valid @RequestBody EmailSendRequest request) {
        EmailSendResponse response = userService.sendEmailVerificationCode(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(
            summary = "회원가입/로그인",
            description = "이메일 인증코드를 검증하여 회원가입 또는 로그인을 처리합니다."
    )
    @ApiResponse(responseCode = "200", description = "인증 성공")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = userService.authenticateUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "토큰 갱신",
            description = "리프레시 토큰으로 새로운 액세스 토큰을 발급받습니다."
    )
    @ApiResponse(responseCode = "200", description = "토큰 갱신 성공")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        AuthResponse response = userService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "현재 로그인된 사용자를 로그아웃 처리합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> logout() {
        userService.logout();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴", description = "현재 로그인된 사용자를 탈퇴 처리합니다.")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok().build();
    }

}
