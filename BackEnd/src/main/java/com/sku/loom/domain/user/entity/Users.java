package com.sku.loom.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    @Schema(description = "회원 고유 번호", example = "1")
    private Long userId;

    @Column(name = "user_email", nullable = false, unique = true)
    @Schema(description = "회원 이메일", example = "user@example.com")
    private String userEmail;

    @Column(name = "user_refresh_token")
    @Schema(description = "리프레시 토큰")
    private String userRefreshToken;

    @CreationTimestamp
    @Column(name = "user_created_at", nullable = false, updatable = false)
    @Schema(description = "회원 가입일")
    private LocalDateTime userCreatedAt;

    // 리프레시 토큰 업데이트 메서드
    public void updateRefreshToken(String refreshToken) {
        this.userRefreshToken = refreshToken;
    }

    // 리프레시 토큰 삭제 (로그아웃 시)
    public void clearRefreshToken() {
        this.userRefreshToken = null;
    }
}