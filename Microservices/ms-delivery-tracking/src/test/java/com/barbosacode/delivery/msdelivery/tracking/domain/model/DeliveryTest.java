package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import com.barbosacode.delivery.msdelivery.tracking.domain.enums.DeliveryStatus;
import com.barbosacode.delivery.msdelivery.tracking.domain.operations.PreparationDetails;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.PhoneNumber;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTest {

    private final Faker faker = new Faker();

    private PreparationDetails buildPreparationDetails() {
        return PreparationDetails.builder()
                .sender(buildContact())
                .recipient(buildContact())
                .distanceFee(BigDecimal.valueOf(15))
                .courierPayout(BigDecimal.valueOf(5))
                .expectedDeliveryTime(Duration.ofHours(2))
                .build();

    }

    private ContactPoint buildContact() {
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

    private PhoneNumber builderPhoneNumber() {
        return PhoneNumber.builder().value(faker.phoneNumber().cellPhone()).build();
    }

    @Test
    @DisplayName("Deve alterar o status para AGUARDANDO_ENTREGADOR ao realizar a entrega")
    public void shouldChangeStatusToPlaced() {
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(buildPreparationDetails());
        delivery.place();
        assertAll(
                () -> assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus()),
                () -> assertNotNull(delivery.getPlacedAt())
        );
    }
}
