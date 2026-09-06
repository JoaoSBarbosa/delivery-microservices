package com.barbosacode.delivery.msdelivery.tracking.domain.valueObject;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactPoint {

    private String zipCode;
    private String streetAddress;
    private String streetNumber;
    private String city;
    private String state;
    private String country;
    private String complement;
    private String name;

    @Embedded
    private PhoneNumber phoneNumber;


    public ContactPoint(
            String zipCode,
            String streetAddress,
            String streetNumber,
            String city,
            String state,
            String country,
            String complement,
            String name,
            PhoneNumber phoneNumber
    ) {
        this.zipCode = requireNonBlank(zipCode, "CEP");
        this.streetAddress = requireNonBlank(streetAddress, "Endereço");
        this.streetNumber = requireNonBlank(streetNumber, "Número");
        this.city = requireNonBlank(city, "Cidade");
        this.state = requireNonBlank(state, "Estado");
        this.country = requireNonBlank(country, "País");
        this.complement = complement;
        this.name = requireNonBlank(name, "Nome");
        this.phoneNumber = Objects.requireNonNull(
                phoneNumber,
                "Telefone não pode ser nulo"
        );
    }

    private static String requireNonBlank(String value, String field) {
        Objects.requireNonNull(value, field + " não pode ser nulo");

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    field + " não pode estar vazio"
            );
        }

        return value;
    }
}