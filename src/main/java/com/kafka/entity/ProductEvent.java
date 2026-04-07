package com.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
    
    private String eventType;     // CREATE, UPDATE, DELETE
    private Long productId;
    private Product productData;
    private LocalDateTime eventTimestamp;
    private String source;        // API, Service, etc.
    
    // Helper method to create events
    public static ProductEvent create(Product product, String eventType) {
        return new ProductEvent(
            eventType,
            product.getId(),
            product,
            LocalDateTime.now(),
            "kafka-crud-project"
        );
    }
}