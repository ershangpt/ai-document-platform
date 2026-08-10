package com.shan.aidoc.aiservice.rag.dto;

import java.util.UUID;

public record RagSource(
        UUID documentId,
        String fileName,
        String chunkText
) {
}