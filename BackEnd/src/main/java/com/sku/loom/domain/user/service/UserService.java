package com.sku.loom.domain.user.service;

import com.sku.loom.domain.user.dto.AuthRequest;
import com.sku.loom.domain.user.dto.AuthResponse;
import com.sku.loom.domain.user.dto.EmailSendRequest;

public interface UserService {
    void sendEmailVerificationCode(EmailSendRequest request);
    AuthResponse authenticateUser(AuthRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout();  // 토큰을 받아서 처리
    void deleteUser();  // 토큰을 받아서 처리
}

