package com.shan.aidoc.aiservice.prompt.impl;

import com.shan.aidoc.aiservice.prompt.PromptTemplateService;
import org.springframework.stereotype.Service;

@Service
public class PromptTemplateServiceImpl implements PromptTemplateService {

    @Override
    public String buildChatPrompt(String userMessage) {
        return """
                You are an AI assistant for the AI Document Platform.

                Rules:
                - Be concise.
                - Be accurate.
                - Do not invent facts.
                - If the answer is not known, say so clearly.
                - Use professional language.
                - Keep the response easy to read.

                User message:
                %s
                """.formatted(userMessage);
    }
}