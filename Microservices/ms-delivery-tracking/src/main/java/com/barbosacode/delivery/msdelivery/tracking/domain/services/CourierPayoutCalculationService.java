package com.barbosacode.delivery.msdelivery.tracking.domain.services;

import java.math.BigDecimal;

public interface CourierPayoutCalculationService {


    BigDecimal calculatePayout(Double distanceInKm);
}
