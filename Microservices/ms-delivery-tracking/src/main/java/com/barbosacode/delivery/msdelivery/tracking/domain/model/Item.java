package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    @EqualsAndHashCode.Include
    private UUID id;
    private String description;
    private String name;
    private Integer quantity;

    static Item BrandNew(String name, Integer quantity) {
        Item item = new Item();

        item.id = UUID.randomUUID();
        item.name = name;
        item.quantity = quantity;
        return item;
    }
}
