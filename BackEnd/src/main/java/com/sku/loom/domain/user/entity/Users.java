package com.sku.loom.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    @Schema(description = "회원 아이디", example = "1")
    private long userId;

    @Column(name = "user_email", nullable = false)
    @Schema(description = "회원 이메일", example = "1")
    private String userEmail;

    @Column(name = "user_refresh_token", nullable = false)
    @Schema(description = "회원 리프레시 토큰", example = "1")
    private String userRefreshToken;

    @Column(name = "user_created_at", nullable = false)
    @Schema(description = "회원 생성 일자", example = "1")
    private Timestamp userCreatedAt;

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", userRefreshToken='" + userRefreshToken + '\'' +
                ", userCreatedAt=" + userCreatedAt +
                '}';
    }
}
