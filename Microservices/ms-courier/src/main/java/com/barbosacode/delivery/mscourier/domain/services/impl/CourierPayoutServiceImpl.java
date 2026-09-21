package com.barbosacode.delivery.mscourier.domain.services.impl;

import com.barbosacode.delivery.mscourier.domain.services.CourierPayoutService;
import com.barbosacode.delivery.mscourier.integration.dto.request.CourierPayoutRequest;
import com.barbosacode.delivery.mscourier.integration.dto.response.CourierPayoutResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CourierPayoutServiceImpl implements CourierPayoutService {
    @Override
    public CourierPayoutResponse calculate(CourierPayoutRequest request) {

        BigDecimal payoutFee = new BigDecimal("10")
                .multiply(new BigDecimal(request.getDistanceInKm()))
                .setScale(2, RoundingMode.HALF_EVEN);

        return new CourierPayoutResponse(payoutFee);

    }
}
