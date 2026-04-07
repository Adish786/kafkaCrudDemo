package com.kafka.service;

import com.kafka.entity.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    
    /**
     * Consume product events from Kafka topic
     * @KafkaListener listens to the specified topic
     */
    @KafkaListener(
        topics = "${spring.kafka.template.default-topic}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeProductEvent(ProductEvent event) {
        log.info("Received product event: Type={}, Product ID={}, Timestamp={}", 
            event.getEventType(), 
            event.getProductId(), 
            event.getEventTimestamp());
        
        // Process the event based on its type
        switch (event.getEventType()) {
            case "CREATE":
                log.info("Processing CREATE event for product: {}", event.getProductData().getName());
                // Additional business logic for product creation
                break;
            case "UPDATE":
                log.info("Processing UPDATE event for product ID: {}", event.getProductId());
                // Additional business logic for product updates
                break;
            case "DELETE":
                log.info("Processing DELETE event for product ID: {}", event.getProductId());
                // Additional business logic for product deletion
                break;
            default:
                log.warn("Unknown event type: {}", event.getEventType());
        }
    }
}