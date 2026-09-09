package com.barbosacode.delivery.msdelivery.tracking.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactPointRequest {

    @NotBlank(message = "CEP é obrigatório")
    private String zipCode;
    @NotBlank(message = "Endereço é obrigatório")
    private String streetAddress;
    @NotBlank(message = "Número é obrigatório")
    private String streetNumber;
    @NotBlank(message = "Cidade é obrigatória")
    private String city;
    @NotBlank(message = "Estado é obrigatório")
    private String state;
    @NotBlank(message = "País é obrigatório")
    private String country;
    private String complement;
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Telefone é obrigatório")
    @Valid
    private PhoneNumberRequest phoneNumber;


}
