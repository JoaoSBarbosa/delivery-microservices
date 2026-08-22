package com.barbosacode.delivery.msdelivery.tracking.domain.model;


import com.barbosacode.delivery.msdelivery.tracking.domain.enums.DeliveryStatus;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Delivery {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID courierId;
    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulfilledAt;
    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;
    private Integer totalItems;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    private ContactPoint sender;
    private ContactPoint recipient;
    private List<Item> items = new ArrayList<>();


    public static Delivery draft() {
        Delivery delivery = new Delivery();

        delivery.setId(UUID.randomUUID());
        delivery.setStatus(DeliveryStatus.DRAFT);
        delivery.setDistanceFee(BigDecimal.ZERO);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setTotalItems(0);
        return delivery;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    }

}
