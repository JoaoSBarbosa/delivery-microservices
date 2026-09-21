package com.barbosacode.delivery.mscourier.integration.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AssignedDeliveryResponse(
        UUID id,
        OffsetDateTime assignedAt

) {
}
