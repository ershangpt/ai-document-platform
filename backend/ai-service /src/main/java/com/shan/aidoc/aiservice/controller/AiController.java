package com.shan.aidoc.aiservice.controller;

import com.shan.aidoc.aiservice.dto.ChatRequest;
import com.shan.aidoc.aiservice.dto.ChatResponse;
import com.shan.aidoc.aiservice.service.AiChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AiController {

    private final AiChatService aiChatService;

    private AiController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @GetMapping("/ai/chat")
    public ResponseEntity<ChatResponse> getMessage(@RequestBody ChatRequest msg) {
        return ResponseEntity.ok(new ChatResponse(aiChatService.chat(msg.message())));
    }
}