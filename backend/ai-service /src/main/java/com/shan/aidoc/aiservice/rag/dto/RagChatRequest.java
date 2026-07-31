package com.shan.aidoc.aiservice.rag.dto;

import java.util.UUID;

public record RagChatRequest(
        String question,
        UUID documentId
) {
}