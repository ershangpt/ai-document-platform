package com.shan.aidoc.userservice.service;

import com.shan.aidoc.userservice.dto.CreateDocumentRequest;
import com.shan.aidoc.userservice.dto.DocumentResponse;
import com.shan.aidoc.userservice.entity.Document;
import com.shan.aidoc.userservice.entity.User;
import com.shan.aidoc.userservice.exception.UserNotFoundException;
import com.shan.aidoc.userservice.repository.DocumentRepository;
import com.shan.aidoc.userservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public DocumentResponse createDocument(CreateDocumentRequest request) {
        userRepository.findById(request.userId()).orElseThrow(() ->
                new UserNotFoundException("User with id " + request.userId() + " not found"));

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
