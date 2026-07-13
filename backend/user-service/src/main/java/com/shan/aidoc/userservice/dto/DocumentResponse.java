package com.shan.aidoc.userservice.dto;

import com.shan.aidoc.userservice.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentResponse(
        UUID id,
        String title,
        String content,
        UUID user,
        LocalDateTime createdAt
) {
}
