package com.barbosacode.delivery.msdelivery.tracking.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {


    private String description;
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantity;
}
