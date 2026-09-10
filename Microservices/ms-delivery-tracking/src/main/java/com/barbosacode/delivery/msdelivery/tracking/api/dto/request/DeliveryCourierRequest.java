package com.barbosacode.delivery.msdelivery.tracking.api.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeliveryCourierRequest {
    @NotNull(message = "O ID do entregador não pode ser nulo.")
    private UUID courierId;

}
