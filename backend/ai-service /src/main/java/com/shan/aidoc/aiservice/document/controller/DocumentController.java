package com.shan.aidoc.aiservice.document.controller;

import com.shan.aidoc.aiservice.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(
            value = "/upload",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file) {

        documentService.upload(file);

        return ResponseEntity.ok("Document uploaded successfully.");
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String query) {

        return documentService.search(query);
    }
}