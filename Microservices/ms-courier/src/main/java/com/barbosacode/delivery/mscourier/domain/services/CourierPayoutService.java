package com.barbosacode.delivery.mscourier.domain.services;

import com.barbosacode.delivery.mscourier.api.dto.request.CourierPayoutRequest;
import com.barbosacode.delivery.mscourier.api.dto.response.CourierPayoutResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface CourierPayoutService {

    CourierPayoutResponse calculate(@RequestBody CourierPayoutRequest request);
}
