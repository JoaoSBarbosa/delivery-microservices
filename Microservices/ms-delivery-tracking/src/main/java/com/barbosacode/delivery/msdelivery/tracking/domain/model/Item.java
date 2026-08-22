package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    @EqualsAndHashCode.Include
    private UUID id;
    private String description;
    private String name;
    @Setter(AccessLevel.PACKAGE)
    private Integer quantity;

    static Item brandNew(String name, Integer quantity) {
        return brandNew(name, quantity, null);
    }

    static Item brandNew(String name, Integer quantity, String description) {
        Item item = new Item();

        item.setId(UUID.randomUUID());
        item.setName(name);
        item.setQuantity(quantity);
        item.setDescription(description);
        return item;
    }
}
