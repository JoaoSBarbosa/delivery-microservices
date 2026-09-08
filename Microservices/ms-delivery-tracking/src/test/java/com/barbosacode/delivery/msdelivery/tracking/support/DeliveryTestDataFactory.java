package com.barbosacode.delivery.msdelivery.tracking.support;

import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.PhoneNumber;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.PreparationDetails;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.Duration;

public final class DeliveryTestDataFactory {

    private static final Faker faker = new Faker();

    public static PreparationDetails buildPreparationDetails() {
        return PreparationDetails.builder()
                .sender(buildContact())
                .recipient(buildContact())
                .distanceFee(BigDecimal.valueOf(15))
                .courierPayout(BigDecimal.valueOf(5))
                .expectedDeliveryTime(Duration.ofHours(2))
                .build();

    }

    private static ContactPoint buildContact() {
        return ContactPoint.builder()
                .zipCode(faker.address().zipCode())
                .streetAddress(faker.address().streetAddress())
                .streetNumber(String.valueOf(faker.number().numberBetween(1, 9999)))
                .city(faker.address().city())
                .state(faker.address().stateAbbr())
                .country("Brasil")
                .complement(null)
                .name(faker.name().fullName())
                .phoneNumber(builderPhoneNumber()).build();
    }

    private static PhoneNumber builderPhoneNumber() {
        return PhoneNumber.builder().value(faker.phoneNumber().cellPhone()).build();
    }
}
