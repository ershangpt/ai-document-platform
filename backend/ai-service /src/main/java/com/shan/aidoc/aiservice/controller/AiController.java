package com.shan.aidoc.aiservice.controller;

import com.shan.aidoc.aiservice.dto.ChatRequest;
import com.shan.aidoc.aiservice.dto.ChatResponse;
import com.shan.aidoc.aiservice.service.AiChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiChatService aiChatService;

    private AiController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest msg) {
        return ResponseEntity.ok(new ChatResponse(aiChatService.chat(msg.message())));
    }
}