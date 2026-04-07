package com.kafka.service;

import com.kafka.entity.Product;
import com.kafka.entity.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    
    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;
    
    @Value("${spring.kafka.template.default-topic}")
    private String topicName;
    
    /**
     * Send product event to Kafka topic
     */
    public void sendProductEvent(Product product, String eventType) {
        ProductEvent event = ProductEvent.create(product, eventType);
        
        CompletableFuture<SendResult<String, ProductEvent>> future = 
            kafkaTemplate.send(topicName, String.valueOf(product.getId()), event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Event sent successfully: Event Type={}, Product ID={}, Offset={}", 
                    eventType, product.getId(), result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send event: Event Type={}, Product ID={}, Error={}", 
                    eventType, product.getId(), ex.getMessage());
            }
        });
    }
}