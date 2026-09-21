package com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.adapter;

import com.barbosacode.delivery.msdelivery.tracking.domain.ports.CourierPayoutPort;
import com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.CourierAPIClient;
import com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.dto.CourierPayoutRequest;
import com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.dto.CourierPayoutResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CourierPayoutAdapter implements CourierPayoutPort {

    private final CourierAPIClient apiClient;

    public CourierPayoutAdapter(CourierAPIClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public BigDecimal calculatePayoutFee(Double distanceInKm) {
        CourierPayoutRequest request = new CourierPayoutRequest(distanceInKm);
        CourierPayoutResponse response = apiClient.payoutCalculation(request);

        return response.payoutFee();
    }
}
