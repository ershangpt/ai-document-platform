package com.shan.aidoc.userservice.repository;

import com.shan.aidoc.userservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
