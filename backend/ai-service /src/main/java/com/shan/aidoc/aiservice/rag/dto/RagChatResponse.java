package com.shan.aidoc.aiservice.rag.dto;

import java.util.List;
import java.util.UUID;

public record RagChatResponse(
        UUID documentId,
        String answer,
        List<RagSource> sources
) {
}