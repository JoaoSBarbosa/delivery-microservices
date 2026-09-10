package com.barbosacode.delivery.msdelivery.tracking.domain.services;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;

import java.util.UUID;

public interface DeliveryCheckpointService {

    DeliveryResponse place(UUID deliveryId);

    DeliveryResponse complete(UUID deliveryId);

    DeliveryResponse pickup(UUID deliveryId, UUID courierId);

}
