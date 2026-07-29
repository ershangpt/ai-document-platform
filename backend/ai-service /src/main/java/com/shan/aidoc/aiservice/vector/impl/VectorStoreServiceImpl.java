package com.shan.aidoc.aiservice.vector.impl;

import com.shan.aidoc.aiservice.vector.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VectorStoreServiceImpl implements VectorStoreService {

    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;

    @Override
    public void save(String id, String content) {

        Document document = new Document(
                id,
                content,
                Map.of(
                        "source", "manual",
                        "documentId", id
                )
        );

        vectorStore.add(List.of(document));

        log.info("Document stored successfully : {}", id);
    }

    @Override
    public List<Document> search(String query) {

        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(5)
                .build();

        return vectorStore.similaritySearch(request);
    }
}