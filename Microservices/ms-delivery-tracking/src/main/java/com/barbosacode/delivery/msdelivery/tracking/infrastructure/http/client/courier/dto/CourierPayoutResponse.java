package com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.dto;

import java.math.BigDecimal;

public record CourierPayoutResponse(BigDecimal payoutFee) {
}
