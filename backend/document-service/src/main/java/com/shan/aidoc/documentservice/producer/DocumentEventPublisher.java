package com.shan.aidoc.documentservice.producer;


import com.shan.aidoc.events.DocumentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentEventPublisher {

    private final KafkaTemplate<String, DocumentCreatedEvent> kafkaTemplate;

    public DocumentEventPublisher(KafkaTemplate<String, DocumentCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(DocumentCreatedEvent event) {
        kafkaTemplate.send("document-events", event);
    }
}