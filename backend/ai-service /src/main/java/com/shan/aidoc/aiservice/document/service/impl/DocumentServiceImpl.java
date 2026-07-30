package com.shan.aidoc.aiservice.document.service.impl;

import com.shan.aidoc.aiservice.document.dto.DocumentSearchResponse;
import com.shan.aidoc.aiservice.document.dto.DocumentUploadResponse;
import com.shan.aidoc.aiservice.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final VectorStore vectorStore;

    @Override
    public DocumentUploadResponse upload(MultipartFile file) {
        UUID documentId = UUID.randomUUID();

        try {
            Resource resource = new InputStreamResource(file.getInputStream());

            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);
            List<Document> pages = pdfReader.get();

            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> chunks = splitter.apply(pages);

            int chunkIndex = 0;
            for (Document chunk : chunks) {
                Map<String, Object> metadata = new LinkedHashMap<>(chunk.getMetadata());
                metadata.put("documentId", documentId.toString());
                metadata.put("fileName", file.getOriginalFilename());
                metadata.put("contentType", file.getContentType());
                metadata.put("uploadedAt", Instant.now().toString());
                metadata.put("chunkIndex", chunkIndex++);
                metadata.put("source", "pdf-upload");

                chunk.getMetadata().clear();
                chunk.getMetadata().putAll(metadata);
            }

            vectorStore.add(chunks);

            log.info("Uploaded file: {}", file.getOriginalFilename());
            log.info("Pages read: {}", pages.size());
            log.info("Chunks stored: {}", chunks.size());

            return new DocumentUploadResponse(
                    documentId,
                    file.getOriginalFilename(),
                    pages.size(),
                    chunks.size()
            );

        } catch (IOException e) {
            throw new RuntimeException("Failed to process uploaded PDF.", e);
        }
    }

    @Override
    public List<DocumentSearchResponse> search(String query, UUID documentId) {

        SearchRequest.Builder builder = SearchRequest.builder()
                .query(query)
                .topK(5)
                .similarityThreshold(0.5);

        if (documentId != null) {
            builder.filterExpression("documentId == '" + documentId + "'");
        } else {
            builder.filterExpression("source == 'pdf-upload'");
        }

        List<Document> results = vectorStore.similaritySearch(builder.build());

        return results.stream()
                .map(document -> new DocumentSearchResponse(
                        parseUuid(document.getMetadata().get("documentId")),
                        document.getText(),
                        document.getMetadata(),
                        extractScore(document)
                ))
                .toList();
    }

    private UUID parseUuid(Object value) {
        if (value == null) {
            return null;
        }
        return UUID.fromString(String.valueOf(value));
    }

    private double extractScore(Document document) {
        Object score = document.getMetadata().get("score");
        if (score instanceof Number number) {
            return number.doubleValue();
        }
        return 0.0d;
    }
}