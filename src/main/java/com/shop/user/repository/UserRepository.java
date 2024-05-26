package com.shop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    Optional<User> findByProviderId(String providerId);
}
