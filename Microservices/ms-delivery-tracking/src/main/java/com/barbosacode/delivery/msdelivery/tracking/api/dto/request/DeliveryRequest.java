package com.barbosacode.delivery.msdelivery.tracking.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DeliveryRequest {

    @NotNull
    @Valid
    private ContactPointRequest sender;
    @NotNull
    @Valid
    private ContactPointRequest recipient;
    @NotEmpty
    @Valid
    private List<ItemRequest> items = new ArrayList<>();

}
