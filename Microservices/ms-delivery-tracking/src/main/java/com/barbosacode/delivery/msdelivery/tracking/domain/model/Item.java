package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode( onlyExplicitlyIncluded = true)
public class Item {

    @EqualsAndHashCode.Include
    private UUID id;
    private String description;
    private String name;
    private String quantity;
}
