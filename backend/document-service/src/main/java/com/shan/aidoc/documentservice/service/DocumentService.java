package com.shan.aidoc.documentservice.service;

import com.shan.aidoc.documentservice.dto.CreateDocumentRequest;
import com.shan.aidoc.documentservice.dto.DocumentResponse;
import com.shan.aidoc.documentservice.entity.Document;
import com.shan.aidoc.documentservice.repository.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentResponse createDocument(CreateDocumentRequest request) {
        Document document = new Document(
                request.title(),
                request.content(),
                request.userId()
        );

        Document savedDocument = documentRepository.save(document);

        return toResponse(savedDocument);
    }

    private DocumentResponse toResponse(Document savedDocument) {
       return new DocumentResponse(savedDocument.getId(), savedDocument.getTitle(), savedDocument.getContent(), savedDocument.getUserId(), savedDocument.getCreatedAt());
    }

    public Page<DocumentResponse> getDocuments(Pageable pageable) {
        return documentRepository.findAll(pageable).map(this::toResponse);
    }

}
