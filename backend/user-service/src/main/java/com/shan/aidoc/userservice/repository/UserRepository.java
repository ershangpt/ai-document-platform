package com.shan.aidoc.userservice.repository;

import com.shan.aidoc.userservice.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Page<User> findByFirstNameContainingIgnoreCase(
            String firstName,
            Pageable pageable
    );
    Page<User> findByEmailContainingIgnoreCase(
            String email,
            Pageable pageable
    );
}