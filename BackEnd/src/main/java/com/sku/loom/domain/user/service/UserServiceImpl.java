package com.sku.loom.domain.user.service;

import com.sku.loom.domain.user.dto.AuthRequest;
import com.sku.loom.domain.user.dto.AuthResponse;
import com.sku.loom.domain.user.dto.EmailSendRequest;
import com.sku.loom.domain.user.dto.EmailSendResponse;
import com.sku.loom.domain.user.entity.Users;
import com.sku.loom.domain.user.repository.UserRepository;
import com.sku.loom.global.exception.base.CustomException;
import com.sku.loom.global.exception.constant.ErrorDetail;
import com.sku.loom.global.service.EmailService;
import com.sku.loom.global.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;

    // 수정: EmailSendResponse 반환
    @Override
    public EmailSendResponse sendEmailVerificationCode(EmailSendRequest request) {
        log.info("이메일 인증코드 발송 요청: {}", request.getEmail());

        // EmailService에서 인증코드 받기
        String verificationCode = emailService.sendVerificationCode(request.getEmail());

        // 응답 생성 (인증코드 포함)
        return EmailSendResponse.builder()
                .message("인증코드가 발송되었습니다.")
                .verificationCode(verificationCode)
                .build();
    }

    @Override
    public AuthResponse authenticateUser(AuthRequest request) {
        log.info("사용자 인증 요청: {}", request.getEmail());

        // 1. 이메일 인증코드 검증
        if (!emailService.verifyCode(request.getEmail(), request.getCode())) {
            throw new CustomException(ErrorDetail.INVALID_VERIFICATION_CODE);
        }

        // 2. DB에서 사용자 조회
        Optional<Users> existingUser = userRepository.findByUserEmail(request.getEmail());

        Users user;
        boolean isNewUser = false;

        if (existingUser.isPresent()) {
            user = existingUser.get();
            log.info("기존 사용자 로그인: {}", user.getUserEmail());
        } else {
            user = Users.builder()
                    .userEmail(request.getEmail())
                    .build();
            user = userRepository.save(user);
            isNewUser = true;
            log.info("신규 사용자 회원가입: {}", user.getUserEmail());
        }

        // 3. JWT 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        // 4. Refresh Token DB 저장
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isNewUser(isNewUser)
                .userId(user.getUserId())
                .email(user.getUserEmail())
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        // 1. JWT 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(refreshToken) ||
                !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new CustomException(ErrorDetail.INVALID_REFRESH_TOKEN);
        }

        // 2. DB에서 사용자 조회
        Users user = userRepository.findByUserRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorDetail.INVALID_REFRESH_TOKEN));

        // 3. 새로운 토큰들 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user.getUserId()); // 새로 생성

        // 4. 새로운 리프레시 토큰을 DB에 저장
        user.updateRefreshToken(newRefreshToken);
        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)    // 새 리프레시 토큰 반환
                .userId(user.getUserId())
                .email(user.getUserEmail())
                .build();
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("인증 정보가 없습니다.");
            throw new CustomException(ErrorDetail.INVALID_TOKEN);
        }

        String email = authentication.getName();
        log.info("로그아웃 요청 - 이메일: {}", email);

        Users user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new CustomException(ErrorDetail.NOT_FOUND_USER));

        user.clearRefreshToken();
        userRepository.save(user);

        log.info("✅ 로그아웃 완료: {}", email);
    }

    @Override
    public void deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("인증 정보가 없습니다.");
            throw new CustomException(ErrorDetail.INVALID_TOKEN);
        }

        String email = authentication.getName();
        log.info("회원 탈퇴 요청 - 이메일: {}", email);

        Users user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new CustomException(ErrorDetail.NOT_FOUND_USER));

        userRepository.delete(user);
        log.info("✅ 회원 탈퇴 완료: {}", email);
    }
}
