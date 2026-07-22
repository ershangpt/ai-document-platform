package com.shan.aidoc.aiservice.consumer;


import com.shan.aidoc.events.DocumentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentEventConsumer {

    @KafkaListener(
            topics = "document-events",
            groupId = "ai-group"
    )
    public void consume(DocumentCreatedEvent event) {

        System.out.println("Received Event: From AI Service");

        System.out.println(event);

    }
}