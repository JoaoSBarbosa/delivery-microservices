package com.barbosacode.delivery.msdelivery.tracking.domain.valueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Builder
@AllArgsConstructor
public class PreparationDetails {

    private ContactPoint sender;
    private ContactPoint recipient;
    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private Duration expectedDeliveryTime;
}
