package com.shan.aidoc.aiservice.controller;

import com.shan.aidoc.aiservice.dto.ChatRequest;
import com.shan.aidoc.aiservice.dto.ChatResponse;
import com.shan.aidoc.aiservice.embedding.EmbeddingService;
import com.shan.aidoc.aiservice.service.AiChatService;
import com.shan.aidoc.aiservice.vector.VectorStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiChatService aiChatService;
    private final EmbeddingService embeddingService;
    private final VectorStoreService vectorStoreService;

    private AiController(AiChatService aiChatService, EmbeddingService embeddingService, VectorStoreService vectorStoreService) {
        this.aiChatService = aiChatService;
        this.embeddingService = embeddingService;
        this.vectorStoreService = vectorStoreService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest msg) {
        return ResponseEntity.ok(new ChatResponse(aiChatService.chat(msg.message())));
    }

    @PostMapping("/embed")
    public float[] embed(@RequestBody Map<String, String> request) {
        return embeddingService.embedText(request.get("text"));
    }

    @PostMapping("/vector")
    public ResponseEntity<String> save(@RequestBody ChatRequest request) {

        vectorStoreService.save(
                UUID.randomUUID().toString(),
                request.message()
        );

        return ResponseEntity.ok("Stored successfully");
    }
}