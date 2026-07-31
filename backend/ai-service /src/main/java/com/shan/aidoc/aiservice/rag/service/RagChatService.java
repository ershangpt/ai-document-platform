package com.shan.aidoc.aiservice.rag.service;

import com.shan.aidoc.aiservice.rag.dto.RagChatResponse;

import java.util.UUID;

public interface RagChatService {
    RagChatResponse ask(String question, UUID documentId);
}