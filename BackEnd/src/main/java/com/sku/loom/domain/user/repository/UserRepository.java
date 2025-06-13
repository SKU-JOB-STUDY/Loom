package com.sku.loom.domain.user.repository;

import com.sku.loom.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserId(long userId);
    Optional<Users> findByUserEmail(String userEmail);
    Optional<Users> findByUserRefreshToken( String refreshToken);
}