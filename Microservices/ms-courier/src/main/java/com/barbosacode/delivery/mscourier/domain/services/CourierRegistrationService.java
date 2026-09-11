package com.barbosacode.delivery.mscourier.domain.services;

import com.barbosacode.delivery.mscourier.api.dto.request.CourierRequest;
import com.barbosacode.delivery.mscourier.api.dto.response.CourierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourierRegistrationService {

    CourierResponse create(CourierRequest courierRequest);

    CourierResponse assignDeliveryToCourier(UUID deliveryId);

    CourierResponse fulfill(UUID deliveryId);

    CourierResponse findCourierById(UUID courierId);

    Page<CourierResponse> findCouriers(Pageable pageable);

    CourierResponse update(UUID deliveryId, CourierRequest courierRequest);

    void delete(UUID courierId);
}
