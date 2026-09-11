package com.barbosacode.delivery.mscourier.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierRequest {

    @NotBlank(message = "Nome do entregador é obrigatório")
    private String courierName;
    @NotBlank(message = "Número de telefone do entregador é obrigatório")
    private String phoneNumber;


}
