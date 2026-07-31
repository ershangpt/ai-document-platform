package com.shan.aidoc.aiservice.rag.controller;

import com.shan.aidoc.aiservice.rag.dto.RagChatRequest;
import com.shan.aidoc.aiservice.rag.dto.RagChatResponse;
import com.shan.aidoc.aiservice.rag.service.RagChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rag")
@RequiredArgsConstructor
public class RagChatController {

    private final RagChatService ragChatService;

    @PostMapping("/ask")
    public ResponseEntity<RagChatResponse> ask(@RequestBody RagChatRequest request) {
        return ResponseEntity.ok(ragChatService.ask(request.question(), request.documentId()));
    }
}