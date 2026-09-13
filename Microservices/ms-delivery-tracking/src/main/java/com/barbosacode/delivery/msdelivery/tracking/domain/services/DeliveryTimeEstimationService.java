package com.barbosacode.delivery.msdelivery.tracking.domain.services;

import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.DeliveryEstimate;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver);
}
