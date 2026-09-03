package com.barbosacode.delivery.mscourier.domain.model;


import com.barbosacode.delivery.mscourier.domain.valueObject.PhoneNumber;
import jakarta.persistence.Embedded;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Courier {

    @EqualsAndHashCode.Include
    private UUID id;
    @Setter(AccessLevel.PUBLIC)
    private String name;
    @Embedded
    @Setter(AccessLevel.PUBLIC)
    private PhoneNumber phoneNumber;

    private Integer fullFilledDeliveriesQuantity;
    private Integer pendingDeliveriesQuantity;
    private OffsetDateTime lastFullFilledDeliveryAt;

    private List<AssignedDelivery> pendingDeliveries = new ArrayList<>();

    public List<AssignedDelivery> getPendingDeliveries() {
        return Collections.unmodifiableList(this.pendingDeliveries);
    }

    public static Courier brandNew(String name, String phoneNumber) {
        Courier courier = new Courier();

        courier.setId(UUID.randomUUID());
        courier.setName(name);
        courier.setPhoneNumber(new PhoneNumber(phoneNumber));
        courier.setFullFilledDeliveriesQuantity(0);
        courier.setPendingDeliveriesQuantity(0);
        return courier;
    }

    public void assign(UUID deliveryId) {
        this.pendingDeliveries.add(AssignedDelivery.pending(deliveryId));
        setPendingDeliveriesQuantity(this.pendingDeliveries.size());
    }

    public void fulfill(UUID deliveryId) {
        AssignedDelivery assignedDelivery = this.pendingDeliveries.stream().filter(d -> d.getId().equals(deliveryId)).findFirst().orElseThrow();

        this.pendingDeliveries.remove(assignedDelivery);
        setFullFilledDeliveriesQuantity(this.fullFilledDeliveriesQuantity + 1);
        setPendingDeliveriesQuantity(this.pendingDeliveries.size());

        setLastFullFilledDeliveryAt(OffsetDateTime.now());
    }
}
