package com.shan.aidoc.documentservice.service;

import com.shan.aidoc.documentservice.client.UserServiceClient;
import com.shan.aidoc.documentservice.dto.CreateDocumentRequest;
import com.shan.aidoc.documentservice.dto.DocumentResponse;
import com.shan.aidoc.documentservice.dto.UserResponse;
import com.shan.aidoc.documentservice.entity.Document;
import com.shan.aidoc.documentservice.exception.UserNotFoundException;
import com.shan.aidoc.documentservice.producer.DocumentEventPublisher;
import com.shan.aidoc.documentservice.repository.DocumentRepository;
import com.shan.aidoc.commonevents.DocumentCreatedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserServiceClient userServiceClient;
    private final DocumentEventPublisher documentEventPublisher;

    public DocumentService(DocumentRepository documentRepository, UserServiceClient userServiceClient, DocumentEventPublisher documentEventPublisher) {

        this.documentRepository = documentRepository;
        this.userServiceClient = userServiceClient;
        this.documentEventPublisher = documentEventPublisher;
    }

    @Transactional
    public DocumentResponse createDocument(CreateDocumentRequest request) {
        UserResponse user = userServiceClient.getUserById(request.userId());

        if(user == null) {
            throw new UserNotFoundException("user with " + request.userId() + " not found." );
        }

        Document document = new Document(
                request.title(),
                request.content(),
                request.userId()
        );

        Document savedDocument = documentRepository.save(document);

        documentEventPublisher.publish(
                new DocumentCreatedEvent(
                        savedDocument.getId(),
                        savedDocument.getUserId(),
                        savedDocument.getTitle(),
                        savedDocument.getCreatedAt()
                )
        );

        return toResponse(savedDocument);
    }

    private DocumentResponse toResponse(Document savedDocument) {
       return new DocumentResponse(savedDocument.getId(), savedDocument.getTitle(), savedDocument.getContent(), savedDocument.getUserId(), savedDocument.getCreatedAt());
    }

    public Page<DocumentResponse> getDocuments(Pageable pageable) {
        return documentRepository.findAll(pageable).map(this::toResponse);
    }

}
