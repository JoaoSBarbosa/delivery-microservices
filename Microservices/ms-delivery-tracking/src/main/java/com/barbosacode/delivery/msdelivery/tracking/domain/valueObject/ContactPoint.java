package com.barbosacode.delivery.msdelivery.tracking.domain.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Embeddable
@EqualsAndHashCode
public class ContactPoint {

    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @Column(name = "complement")
    private String complement;
    @Column(name = "name")
    private String name;

    @Embedded
    private PhoneNumber phoneNumber;

    protected ContactPoint() {
    }

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