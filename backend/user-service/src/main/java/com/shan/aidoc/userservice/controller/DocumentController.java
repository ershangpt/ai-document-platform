package com.shan.aidoc.userservice.controller;

import com.shan.aidoc.userservice.dto.CreateDocumentRequest;
import com.shan.aidoc.userservice.dto.DocumentResponse;
import com.shan.aidoc.userservice.service.DocumentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/documents")
    public ResponseEntity<DocumentResponse> createDocument(@RequestBody CreateDocumentRequest request) {
        DocumentResponse response = documentService.createDocument(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/documents")
    public Page<DocumentResponse> getDocuments(Pageable pageable) {
        return documentService.getDocuments(pageable);
    }
}
