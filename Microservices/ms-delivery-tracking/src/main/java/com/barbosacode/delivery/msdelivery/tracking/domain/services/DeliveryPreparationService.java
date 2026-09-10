package com.barbosacode.delivery.msdelivery.tracking.domain.services;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.DeliveryRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeliveryPreparationService {

    DeliveryResponse draft(DeliveryRequest deliveryRequest);


    DeliveryResponse getById(UUID deliveryId);

    DeliveryResponse update(UUID deliveryId, DeliveryRequest deliveryRequest);

    void delete(UUID deliveryId);

    Page<DeliveryResponse> getAll(Pageable pageable);
}
