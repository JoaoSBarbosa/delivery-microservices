package com.barbosacode.delivery.mscourier.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "assigned_delivery")
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssignedDelivery {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private OffsetDateTime assignedAt;

    @ManyToOne(optional = false)
    @Getter(AccessLevel.PRIVATE)
    private Courier courier;

    static AssignedDelivery pending(UUID deliveryId, Courier courier) {
        AssignedDelivery assignedDelivery = new AssignedDelivery();
        assignedDelivery.setId(deliveryId);
        assignedDelivery.setAssignedAt(OffsetDateTime.now());
        assignedDelivery.setCourier(courier);
        return assignedDelivery;
    }
}
