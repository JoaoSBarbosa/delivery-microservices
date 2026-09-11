package com.barbosacode.delivery.mscourier.domain.mappers;

import com.barbosacode.delivery.mscourier.api.dto.response.AssignedDeliveryResponse;
import com.barbosacode.delivery.mscourier.api.dto.response.CourierResponse;
import com.barbosacode.delivery.mscourier.domain.model.AssignedDelivery;
import com.barbosacode.delivery.mscourier.domain.model.Courier;

import java.util.List;

public final class CourierMapper {

    public CourierMapper() {

    }

    public static CourierResponse toResponse(Courier entity) {
        return new CourierResponse(
                entity.getId(),
                entity.getName(),
                entity.getPhoneNumber().getValue(),
                entity.getFullFilledDeliveriesQuantity(),
                entity.getPendingDeliveriesQuantity(),
                entity.getLastFullFilledDeliveryAt(),
                toResponseList(entity.getPendingDeliveries()));
    }


    private static AssignedDeliveryResponse toResponse(AssignedDelivery entity) {
        return new AssignedDeliveryResponse(
                entity.getId(),
                entity.getAssignedAt()
        );
    }

    public static List<AssignedDeliveryResponse> toResponseList(List<AssignedDelivery> entities) {
        return entities.stream()
                .map(CourierMapper::toResponse)
                .toList();
    }
}
