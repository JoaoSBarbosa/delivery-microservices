package com.barbosacode.delivery.msdelivery.tracking.infrastructure.events.adapter;

import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryFulfilledEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryPickedUpEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryPlacedEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.ports.out.DeliveryEventHandler;
import com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.IntegrationEventsPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.kafka.configs.KafkaTopicConfig.TOPIC_NAME;


@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryDomainDeliveryEventHandler implements DeliveryEventHandler {

    private final IntegrationEventsPublisher eventsPublisher;

    @Override
    @EventListener
    public void handle(DeliveryPlacedEvent placedEvent) {
        log.info("Evento recebido com sucesso: {}", placedEvent);

        eventsPublisher.publish(placedEvent, placedEvent.deliveryId().toString(), TOPIC_NAME);
    }

    @Override
    @EventListener
    public void handle(DeliveryPickedUpEvent pickupEvent) {
        log.info("Evento recebido com sucesso: {}", pickupEvent);
        eventsPublisher.publish(pickupEvent, pickupEvent.deliveryId().toString(), TOPIC_NAME);


    }

    @Override
    @EventListener
    public void handle(DeliveryFulfilledEvent fulfilledEvent) {
        log.info("Evento recebido com sucesso: {}", fulfilledEvent);
        eventsPublisher.publish(fulfilledEvent, fulfilledEvent.deliveryId().toString(), TOPIC_NAME);

    }
}
