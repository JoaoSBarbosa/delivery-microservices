package com.barbosacode.delivery.msdelivery.tracking.domain.ports;

import java.math.BigDecimal;

public interface CourierPayoutPort {

    BigDecimal calculatePayoutFee(Double distanceInKm);
}
