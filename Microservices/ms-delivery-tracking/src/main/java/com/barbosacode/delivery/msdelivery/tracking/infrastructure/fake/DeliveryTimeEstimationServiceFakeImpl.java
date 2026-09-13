package com.barbosacode.delivery.msdelivery.tracking.infrastructure.fake;

import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryTimeEstimationService;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.DeliveryEstimate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService {

    @Override
    public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
        return new DeliveryEstimate(
                Duration.ofHours(3),
                3.1
        );
    }
}
