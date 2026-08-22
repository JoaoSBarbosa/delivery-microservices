package com.barbosacode.delivery.msdelivery.tracking.domain.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Embeddable
@EqualsAndHashCode
public class PhoneNumber {

    @Column(name = "phone_number")
    private String value;
    protected PhoneNumber(){

    }
    public PhoneNumber(String value){
        this.value = Objects.requireNonNull( value , "O número de telefone não pode ser nulo");

        if ( value.isBlank()){
            throw new IllegalArgumentException("O número de telefone não pode estar em branco");
        }
    }
}
