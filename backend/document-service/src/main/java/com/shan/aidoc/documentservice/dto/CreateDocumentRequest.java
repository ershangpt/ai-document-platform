package com.shan.aidoc.documentservice.dto;

import java.util.UUID;

public record CreateDocumentRequest (
        String title,
        String content,
        UUID userId
){
}
