package com.shan.aidoc.aiservice.document.service.impl;

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
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final VectorStore vectorStore;

    @Override
    public void upload(MultipartFile file) {

        try {

            Resource resource = new InputStreamResource(file.getInputStream());

            PagePdfDocumentReader pdfReader =
                    new PagePdfDocumentReader(resource);

            List<Document> documents = pdfReader.get();

            TokenTextSplitter splitter = new TokenTextSplitter();

            List<Document> chunks = splitter.apply(documents);
            log.info("Pages: {}", documents.size());
            log.info("Chunks: {}", chunks.size());

            chunks.forEach(chunk -> {
                log.info("--------------------------------");
                log.info(chunk.getText());
            });

            chunks.forEach(chunk -> {
                chunk.getMetadata().put("fileName", file.getOriginalFilename());
            });

            log.info("Saving {} chunks into pgvector...", chunks.size());

            vectorStore.add(chunks);

            log.info("Successfully stored {} chunks.", chunks.size());

        } catch (IOException e) {
            throw new RuntimeException("Failed to read uploaded PDF.", e);
        }
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