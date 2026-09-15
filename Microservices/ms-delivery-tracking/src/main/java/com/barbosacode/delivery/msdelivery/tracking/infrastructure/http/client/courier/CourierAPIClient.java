package com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier;


import com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.dto.CourierPayoutRequest;
import com.barbosacode.delivery.msdelivery.tracking.infrastructure.http.client.courier.dto.CourierPayoutResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/v1/couriers")
public interface CourierAPIClient {

    @PostExchange("/payout-calculation")
    CourierPayoutResponse payoutCalculation(@RequestBody CourierPayoutRequest request);
}
