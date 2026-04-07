package com.kafka.controller;

import com.kafka.service.KafkaAdminService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/kafka/admin")
public class KafkaAdminController {

    private final KafkaAdminService kafkaAdminService;

    public KafkaAdminController(KafkaAdminService kafkaAdminService) {
        this.kafkaAdminService = kafkaAdminService;
    }

    @GetMapping("/topics")
    public Set<String> listTopics() throws Exception {
        return kafkaAdminService.listAllTopics();
    }

    @PostMapping("/topics")
    public void createTopic(@RequestParam String name,
                            @RequestParam int partitions,
                            @RequestParam short replicationFactor) {
        kafkaAdminService.createTopic(name, partitions, replicationFactor);
    }
}