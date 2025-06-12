package com.sku.loom.domain.user.repository;

import com.sku.loom.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserId(long userId);
    Optional<Users> findByUserEmail(String userEmail);
}
