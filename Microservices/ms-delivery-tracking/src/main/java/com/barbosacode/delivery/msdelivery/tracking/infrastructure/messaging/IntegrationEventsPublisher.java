package com.barbosacode.delivery.msdelivery.tracking.infrastructure.messaging;

public interface IntegrationEventsPublisher {

    void publish(Object object, String keyMessage, String topic);
}
