package com.barbosacode.delivery.mscourier.api.dto.response;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CourierResponse(
        UUID courierId,
        String courierName,
        String phoneNumber,
        Integer fullFilledDeliveriesQuantity,
        Integer pendingDeliveriesQuantity,
        OffsetDateTime lastFullFilledDeliveryAt,
        List<AssignedDeliveryResponse> pendingDeliveries
) {
}
