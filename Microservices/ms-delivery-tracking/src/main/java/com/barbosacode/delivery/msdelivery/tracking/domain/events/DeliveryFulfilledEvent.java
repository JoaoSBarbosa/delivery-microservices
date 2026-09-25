package com.barbosacode.delivery.msdelivery.tracking.domain.events;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DeliveryFulfilledEvent(
        UUID deliveryId,
        UUID courierId,
        OffsetDateTime occurredAt
) {
}