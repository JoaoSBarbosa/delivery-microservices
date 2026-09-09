package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String description;
    private String name;
    @Setter(AccessLevel.PACKAGE)
    private Integer quantity;

    @ManyToOne(optional = false)
    @Getter(AccessLevel.PRIVATE)
    private Delivery delivery;

    static Item brandNew(String name, Integer quantity, Delivery delivery) {
        return brandNew(name, quantity, delivery, null);
    }

    static Item brandNew(String name, Integer quantity, Delivery delivery, String description) {
        Item item = new Item();

        item.setId(UUID.randomUUID());
        item.setName(name);
        item.setQuantity(quantity);
        item.setDelivery(delivery);
        item.setDescription(description);
        return item;
    }
}
