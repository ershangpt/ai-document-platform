package com.shan.aidoc.aiservice.embedding;

public interface EmbeddingService {
    float[] embedText(String text);
}