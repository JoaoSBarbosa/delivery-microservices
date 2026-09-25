package com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.kafka.configs;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory(KafkaProperties kafkaProperties) {
        var configs = kafkaProperties.buildProducerProperties();

        return new DefaultKafkaProducerFactory<>(
                configs,
                new StringSerializer(),
                new JacksonJsonSerializer<>(jsonMapper())
        );
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    private JsonMapper jsonMapper() {
        return JsonMapper.builder().build();
    }
}