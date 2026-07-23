package com.shan.aidoc.commonevents;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentCreatedEvent(
        UUID documentId,
        UUID userId,
        String title,
        LocalDateTime createdAt
) {
}