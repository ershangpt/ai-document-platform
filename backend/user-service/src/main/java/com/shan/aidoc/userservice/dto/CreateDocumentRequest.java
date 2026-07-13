package com.shan.aidoc.userservice.dto;

import com.shan.aidoc.userservice.entity.User;

import java.util.Date;
import java.util.UUID;

public record CreateDocumentRequest (
        String title,
        String content,
        UUID userId
){
}
