package com.shan.aidoc.userservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private String title;
    private String content;
    private UUID userId;

    @CreatedDate // 👈 Automatically populates on save()
    @Column(name = "created_at", nullable = false, updatable = false) // 👈 Prevents modification on updates
    private LocalDateTime createdAt;

    protected Document(){}

    public Document(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID user) {
        this.userId = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
