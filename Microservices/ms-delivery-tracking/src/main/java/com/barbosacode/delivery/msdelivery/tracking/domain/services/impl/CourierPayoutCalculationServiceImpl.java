package com.barbosacode.delivery.msdelivery.tracking.domain.services.impl;


import com.barbosacode.delivery.msdelivery.tracking.domain.ports.out.CourierCompensationCalculator;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.CourierPayoutCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CourierPayoutCalculationServiceImpl implements CourierPayoutCalculationService {

    private final CourierCompensationCalculator courierCompensationCalculator;


    public CourierPayoutCalculationServiceImpl(CourierCompensationCalculator courierCompensationCalculator) {
        this.courierCompensationCalculator = courierCompensationCalculator;
    }

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {

        return courierCompensationCalculator.calculatePayoutFee(distanceInKm);
    }
}
