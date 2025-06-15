// com.sku.loom.domain.user.service.UserService
package com.sku.loom.domain.user.service;

import com.sku.loom.domain.user.dto.AuthRequest;
import com.sku.loom.domain.user.dto.AuthResponse;
import com.sku.loom.domain.user.dto.EmailSendRequest;
import com.sku.loom.domain.user.dto.EmailSendResponse;

public interface UserService {

    EmailSendResponse sendEmailVerificationCode(EmailSendRequest request);  // 반환 타입 변경

    AuthResponse authenticateUser(AuthRequest request);

    AuthResponse refreshToken(String refreshToken);

    void logout();

    void deleteUser();
}
