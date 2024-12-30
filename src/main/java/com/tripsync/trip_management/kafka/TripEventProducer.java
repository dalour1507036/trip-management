package com.tripsync.trip_management.kafka;

import com.tripsync.trip_management.dto.kafka.TripEvent;
import com.tripsync.trip_management.entity.Trip;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(TripEventProducer.class);
    private List<NewTopic> topics;
    private KafkaTemplate<String, TripEvent> kafkaTemplate;

    @Autowired
    public TripEventProducer(List<NewTopic> topics, KafkaTemplate<String, TripEvent> kafkaTemplate) {
        this.topics = topics;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, Trip tripEvent) {
        logger.info("Sending TripEvent to topic {}: {}", topicName, tripEvent);

        // Check if the topic exists in the list
        if (topics.stream().noneMatch(topic -> topic.name().equals(topicName))) {
            logger.warn("Topic {} not found in the configured list.", topicName);
            return;
        }

        Message<Trip> message = MessageBuilder
            .withPayload(tripEvent)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .build();

        kafkaTemplate.send(message);
    }
}
