package com.barbosacode.delivery.msdelivery.tracking.domain.services.impl;


import com.barbosacode.delivery.msdelivery.tracking.domain.ports.CourierPayoutPort;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.CourierPayoutCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CourierPayoutCalculationServiceImpl implements CourierPayoutCalculationService {

    private final CourierPayoutPort courierPayoutPort;


    public CourierPayoutCalculationServiceImpl(CourierPayoutPort courierPayoutPort) {
        this.courierPayoutPort = courierPayoutPort;
    }

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        
        return courierPayoutPort.calculatePayoutFee(distanceInKm);
    }
}
