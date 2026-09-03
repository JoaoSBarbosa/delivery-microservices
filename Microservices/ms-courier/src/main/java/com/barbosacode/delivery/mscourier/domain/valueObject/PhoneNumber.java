package com.barbosacode.delivery.mscourier.domain.valueObject;

import com.barbosacode.delivery.mscourier.domain.exceptions.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Objects;


@Builder
@Embeddable
@EqualsAndHashCode
public class PhoneNumber {


    @Column(name = "phone_number")
    private String value;

    protected PhoneNumber() {
    }


    public PhoneNumber(String value) {
        this.value = Objects.requireNonNull(value, "O numero de telefone não pode ser nulo");

        if (value.isBlank()) throw new DomainException("O número de telefone não pode estar em branco");

    }

}
