package com.shan.aidoc.aiservice.document.dto;

import java.util.Map;
import java.util.UUID;

public record DocumentSearchResponse(
        UUID id,
        String text,
        Map<String, Object> metadata,
        double score
) {
}