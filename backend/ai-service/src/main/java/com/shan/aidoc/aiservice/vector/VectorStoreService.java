package com.shan.aidoc.aiservice.vector;

import org.springframework.ai.document.Document;

import java.util.List;

public interface VectorStoreService {

    void save(String id, String content);

    List<Document> search(String query);
}