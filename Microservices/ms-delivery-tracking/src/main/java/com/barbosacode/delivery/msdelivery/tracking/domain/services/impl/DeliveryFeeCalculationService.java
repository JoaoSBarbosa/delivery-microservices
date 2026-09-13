package com.barbosacode.delivery.msdelivery.tracking.domain.services.impl;

import java.math.BigDecimal;

public interface DeliveryFeeCalculationService {

    BigDecimal calculateFee(Double distanceInKm);
}
