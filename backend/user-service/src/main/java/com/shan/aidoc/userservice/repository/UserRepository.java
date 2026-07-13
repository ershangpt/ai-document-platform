package com.shan.aidoc.userservice.repository;

import com.shan.aidoc.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Page<User> findByFirstNameContainingIgnoreCase(
            String firstName,
            Pageable pageable
    );
    Optional<User> findByEmailIgnoreCase(String email);
}