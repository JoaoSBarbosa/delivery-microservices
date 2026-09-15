package com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierPayoutRequest {
    private Double distanceInKm;

}
