package com.shan.aidoc.aiservice.document.dto;

import java.util.UUID;

public record DocumentUploadResponse(
        UUID documentId,
        String fileName,
        int pagesRead,
        int chunksStored
) {
}