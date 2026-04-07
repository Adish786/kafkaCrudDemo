package com.kafka.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaAdminService {

    private final KafkaAdmin kafkaAdmin;

    public KafkaAdminService(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public Set<String> listAllTopics() throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            return adminClient.listTopics().names().get();
        }
    }

    public void createTopic(String topicName, int partitions, short replicationFactor) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);
            adminClient.createTopics(Set.of(newTopic)).all().get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create topic: " + topicName, e);
        }
    }

    // You can add more methods here for other administrative tasks
}