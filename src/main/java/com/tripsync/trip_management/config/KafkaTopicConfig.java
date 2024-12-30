package com.tripsync.trip_management.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class KafkaTopicConfig {
    private String bootstrapServers = "localhost:9092";
    private List<String> topics= List.of("created_event", "booked_event", "started_event", "complete_event");

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = Map.of(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers
        );
        return new KafkaAdmin(configs);
    }

    @Bean
    public List<NewTopic> createTopics() {
        return topics.stream()
            .map(topicName -> TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build())
            .collect(Collectors.toList());
    }

    @Bean
    public String ensureTopicsExist() {
        try (AdminClient adminClient = AdminClient.create(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers))) {
            topics.forEach(topic -> {
                try {
                    adminClient.createTopics(List.of(TopicBuilder.name(topic).build())).all().get();
                    System.out.println("Successfully created topic: " + topic);
                } catch (Exception e) {
                    if (e.getCause() instanceof TopicExistsException) {
                        System.out.println("Topic already exists: " + topic);
                    } else {
                        System.err.println("Error creating Kafka topic " + topic + ": " + e.getMessage());
                        throw new RuntimeException("Error creating Kafka topic: " + topic, e);
                    }
                }
            });
            return "Kafka topics checked and created if necessary"; // Indicate success
        } catch (Exception e) {
            System.err.println("Failed to connect to Kafka for topic creation: " + e.getMessage());
            throw new RuntimeException("Failed to ensure topics exist", e);
        }
    }

    @PostConstruct
    public void validateConfiguration() {
    }
}
