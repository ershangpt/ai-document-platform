package com.shan.aidoc.aiservice.service.impl;

import com.shan.aidoc.aiservice.prompt.PromptTemplateService;
import com.shan.aidoc.aiservice.service.AiChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;
    private final PromptTemplateService promptTemplateService;


    public AiChatServiceImpl(ChatClient chatClient, PromptTemplateService promptTemplateService) {
        this.chatClient = chatClient;
        this.promptTemplateService = promptTemplateService;
    }

    @Override
    public String chat(String message) {

        String prompt = promptTemplateService.buildChatPrompt(message);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
