package com.shan.aidoc.aiservice.document.dto;

import java.util.UUID;

public record DocumentStatusResponse(
        long totalChunks,
        UUID lastDocumentId
) {
}