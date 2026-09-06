package com.barbosacode.delivery.msdelivery.tracking.domain.valueObject;

import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {

    @Column(name = "phone_number")
    private String value;


    public PhoneNumber(String value) {
        this.value = Objects.requireNonNull(value, "O número de telefone não pode ser nulo");

        if (value.isBlank()) {
            throw new DomainException("O número de telefone não pode estar em branco");
        }
    }
}
