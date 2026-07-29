package com.shan.aidoc.aiservice.document.service;

import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    void upload(MultipartFile file);

    List<Document> search(String query);
}