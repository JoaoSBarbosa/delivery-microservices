package com.barbosacode.delivery.msdelivery.tracking.api.dto.response;

import com.barbosacode.delivery.msdelivery.tracking.domain.enums.DeliveryStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record DeliveryResponse(UUID id,
                               UUID courierId,
                               OffsetDateTime placedAt,
                               OffsetDateTime assignedAt,
                               OffsetDateTime expectedDeliveryAt,
                               OffsetDateTime fulfilledAt,
                               BigDecimal distanceFee,
                               BigDecimal courierPayout,
                               BigDecimal totalCost,
                               Integer totalItems,
                               DeliveryStatus status,
                               ContactPointResponse sender,
                               ContactPointResponse recipient,
                               List<ItemResponse> items) {
}
