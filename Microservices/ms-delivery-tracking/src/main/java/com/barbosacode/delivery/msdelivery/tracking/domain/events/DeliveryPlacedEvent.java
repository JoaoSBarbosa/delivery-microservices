package com.barbosacode.delivery.msdelivery.tracking.domain.events;

import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


public record DeliveryPlacedEvent(
        UUID deliveryId,
        OffsetDateTime occurredAt,
        ContactPoint sender,
        ContactPoint recipient,
        BigDecimal distanceFee,
        BigDecimal courierPayout,
        BigDecimal totalCost,
        Integer totalItems
) {
}