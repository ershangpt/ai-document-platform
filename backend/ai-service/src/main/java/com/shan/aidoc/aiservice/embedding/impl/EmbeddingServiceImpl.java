package com.shan.aidoc.aiservice.embedding.impl;

import com.shan.aidoc.aiservice.embedding.EmbeddingService;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingServiceImpl(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public float[] embedText(String text) {
        return embeddingModel.embed(text);
    }
}