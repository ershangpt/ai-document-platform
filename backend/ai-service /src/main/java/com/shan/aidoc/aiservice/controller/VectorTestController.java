package com.shan.aidoc.aiservice.controller;

import com.shan.aidoc.aiservice.vector.VectorStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vector")
@RequiredArgsConstructor
public class VectorTestController {

    private final VectorStoreService vectorStoreService;

    @PostMapping("/save")
    public String save(@RequestBody String content) {

        String id = UUID.randomUUID().toString();

        vectorStoreService.save(id, content);

        return "Document Saved";
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String query) {

        return vectorStoreService.search(query);
    }
}