package com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.kafka;

import com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.IntegrationEventsPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class IntegrationEventsPublisherKafkaImpl implements IntegrationEventsPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(Object object, String keyMessage, String topic) {

        SendResult<String, Object> result = kafkaTemplate.send(topic, keyMessage, object).join();
        RecordMetadata recordMetadata = result.getRecordMetadata();
        log.info(
                "Mensagem publicada: \n\tTopico: {} \n\tOffset: {}",
                recordMetadata.topic(),
                recordMetadata.offset()
        );
    }
}
