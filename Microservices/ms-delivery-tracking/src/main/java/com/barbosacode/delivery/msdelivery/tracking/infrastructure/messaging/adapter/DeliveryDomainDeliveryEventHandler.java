package com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging.adapter;

import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryFulfilledEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryPickupEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.events.DeliveryPlacedEvent;
import com.barbosacode.delivery.msdelivery.tracking.domain.ports.out.DeliveryEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DeliveryDomainDeliveryEventHandler implements DeliveryEventHandler {


    @Override
    @EventListener
    public void handle(DeliveryPlacedEvent placedEvent) {
        log.info("Evento recebido com sucesso: {}", placedEvent);
    }

    @Override
    @EventListener
    public void handle(DeliveryPickupEvent pickupEvent) {
        log.info("Evento recebido com sucesso: {}", pickupEvent);

    }

    @Override
    @EventListener
    public void handle(DeliveryFulfilledEvent fulfilledEvent) {
        log.info("Evento recebido com sucesso: {}", fulfilledEvent);

    }
}
