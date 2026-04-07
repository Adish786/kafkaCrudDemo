package com.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    
    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    @Value("${kafka.replication.factor:1}")
    private short replicationFactor;

//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configs.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
//        return new KafkaAdmin(configs);
//    }


    //Set custom topic configurations
//    @Bean
//    public NewTopic productEventsTopic() {
//        Map<String, String> configs = new HashMap<>();
//        configs.put("retention.ms", "86400000");   // 1 day
//        configs.put("cleanup.policy", "delete");
//        return new NewTopic(topicName, 3, (short) 1)
//                .configs(configs);
//    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic productEventsTopic() {
        // Now using the configurable replication factor
        return new NewTopic(topicName, 3, replicationFactor);
    }

    // Avoid duplicate topic definitions if you have multiple topics
    @Bean
    public KafkaAdmin.NewTopics multipleTopics() {
        return new KafkaAdmin.NewTopics(
                new NewTopic("product-events", 3, (short) 1),
                new NewTopic("order-events", 5, (short) 1)
        );
    }
}