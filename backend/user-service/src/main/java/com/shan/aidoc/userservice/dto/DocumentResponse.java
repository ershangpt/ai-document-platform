package com.shan.aidoc.userservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentResponse(
        UUID id,
        String title,
        String content,
        UUID userId,
        LocalDateTime createdAt
) {
}
