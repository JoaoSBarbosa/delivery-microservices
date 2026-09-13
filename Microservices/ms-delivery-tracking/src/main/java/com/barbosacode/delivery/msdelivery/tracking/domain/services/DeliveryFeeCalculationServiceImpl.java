package com.barbosacode.delivery.msdelivery.tracking.domain.services;


import com.barbosacode.delivery.msdelivery.tracking.domain.services.impl.DeliveryFeeCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DeliveryFeeCalculationServiceImpl implements DeliveryFeeCalculationService {

    private static final BigDecimal RATE_PER_KM = new BigDecimal("3");

    @Override
    public BigDecimal calculateFee(Double distanceInKm) {
        return RATE_PER_KM.multiply(BigDecimal.valueOf(distanceInKm)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
