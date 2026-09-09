package com.barbosacode.delivery.msdelivery.tracking.api.dto.response;

public record ContactPointResponse(
        String zipCode,
        String streetAddress,
        String streetNumber,
        String city,
        String state,
        String country,
        String complement,
        String name,
        PhoneNumberResponse phoneNumber
) {
}
