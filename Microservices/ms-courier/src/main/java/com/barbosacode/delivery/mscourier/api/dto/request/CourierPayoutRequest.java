package com.barbosacode.delivery.mscourier.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierPayoutRequest {

    @NotBlank(message = "Distancia em KM não pode ser nulo")
    private Double distanceInKm;
}
