package com.barbosacode.delivery.msdelivery.tracking.domain.services;

import java.math.BigDecimal;

public interface DeliveryFeeCalculationService {

    BigDecimal calculateFee(Double distanceInKm);
}
