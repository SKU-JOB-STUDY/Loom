package com.sku.loom.domain.user.repository;

import com.sku.loom.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    @Query("SELECT u FROM Users u WHERE u.userRefreshToken = :refreshToken")
    Optional<Users> findByUserRefreshToken(@Param("refreshToken") String refreshToken);
}