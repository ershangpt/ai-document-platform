package com.shan.aidoc.userservice.dto;

import java.util.UUID;

public record CreateDocumentRequest (
        String title,
        String content,
        UUID userId
){
}
