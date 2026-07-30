package com.shan.aidoc.aiservice.document.service;

import com.shan.aidoc.aiservice.document.dto.DocumentSearchResponse;
import com.shan.aidoc.aiservice.document.dto.DocumentUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    DocumentUploadResponse upload(MultipartFile file);

    List<DocumentSearchResponse> search(String query, UUID documentId);
}