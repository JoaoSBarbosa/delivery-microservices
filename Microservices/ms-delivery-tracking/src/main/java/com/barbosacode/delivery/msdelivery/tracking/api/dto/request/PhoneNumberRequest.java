package com.barbosacode.delivery.msdelivery.tracking.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneNumberRequest {

    @NotBlank(message = "Número do telefone é obrigatório")
    private String number;
}
