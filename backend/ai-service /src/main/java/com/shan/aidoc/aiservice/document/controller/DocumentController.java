package com.shan.aidoc.aiservice.document.controller;

import com.shan.aidoc.aiservice.document.dto.DocumentSearchResponse;
import com.shan.aidoc.aiservice.document.dto.DocumentUploadResponse;
import com.shan.aidoc.aiservice.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(documentService.upload(file));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DocumentSearchResponse>> search(
            @RequestParam String query,
            @RequestParam(required = false) UUID documentId
    ) {
        return ResponseEntity.ok(documentService.search(query, documentId));
    }
}