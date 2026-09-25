package com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.kafka.configs;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String TOPIC_NAME = "deliveries.v1.events";

    @Bean
    public NewTopic deliveryEventsTopic() {
        return TopicBuilder.name(TOPIC_NAME).partitions(3).replicas(1).build();
    }
}
