package com.shan.aidoc.documentservice.repository;

import com.shan.aidoc.documentservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
