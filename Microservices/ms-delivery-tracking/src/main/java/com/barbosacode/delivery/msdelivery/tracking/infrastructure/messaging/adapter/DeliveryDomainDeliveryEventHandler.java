package com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.adapter;

import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryFulfilledEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryPickupEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryPlacedEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.ports.out.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DeliveryDomainEventHandler implements EventPublisher {


    @Override
    @EventListener
    public void publish(DeliveryPlacedEvent placedEvent) {

    }

    @Override
    @EventListener
    public void publish(DeliveryPickupEvent pickupEvent) {

    }

    @Override
    @EventListener
    public void publish(DeliveryFulfilledEvent fulfilledEvent) {

    }
}
