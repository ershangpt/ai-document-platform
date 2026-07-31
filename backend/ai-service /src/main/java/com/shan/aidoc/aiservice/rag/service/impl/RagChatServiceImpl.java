package com.shan.aidoc.aiservice.rag.service.impl;

import com.shan.aidoc.aiservice.rag.dto.RagChatResponse;
import com.shan.aidoc.aiservice.rag.dto.RagSource;
import com.shan.aidoc.aiservice.rag.service.RagChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RagChatServiceImpl implements RagChatService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    @Override
    public RagChatResponse ask(String question, UUID documentId) {

        SearchRequest.Builder builder = SearchRequest.builder()
                .query(question)
                .topK(5)
                .similarityThreshold(0.5);

        if (documentId != null) {
            builder.filterExpression("documentId == '" + documentId + "'");
        } else {
            builder.filterExpression("source == 'pdf-upload'");
        }

        List<Document> chunks = vectorStore.similaritySearch(builder.build());

        String context = chunks.stream()
                .map(Document::getText)
                .reduce("", (a, b) -> a + "\n\n" + b);

        String prompt = """
                You are an AI assistant for the AI Document Platform.

                Use only the provided context to answer.
                If the answer is not in the context, say you do not know.
                Do not invent facts.

                Context:
                %s

                Question:
                %s

                Answer in a concise, helpful format.
                """.formatted(context, question);

        String answer = chatClient
                .prompt(prompt)
                .call()
                .content();

        List<RagSource> sources = chunks.stream()
                .map(chunk -> new RagSource(
                        UUID.fromString(String.valueOf(chunk.getMetadata().get("documentId"))),
                        String.valueOf(chunk.getMetadata().get("fileName")),
                        chunk.getText()
                ))
                .toList();

        return new RagChatResponse(documentId, answer, sources);
    }
}