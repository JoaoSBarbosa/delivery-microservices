package com.barbosacode.delivery.msdelivery.tracking.domain.events;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DeliveryPickedUpEvent(
        UUID deliveryId,
        UUID courierId,
        OffsetDateTime occurredAt
) {
}