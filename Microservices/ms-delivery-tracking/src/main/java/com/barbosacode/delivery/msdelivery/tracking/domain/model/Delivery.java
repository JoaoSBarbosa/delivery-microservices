package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import com.barbosacode.delivery.msdelivery.tracking.domain.enums.DeliveryStatus;
import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainException;
import com.barbosacode.delivery.msdelivery.tracking.domain.operations.PreparationDetails;
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


    public UUID addItem(String name, int quantity) {
        return addItem(name, quantity, null);
    }

    public UUID addItem(String name, int quantity, String description) {
        Item item = Item.brandNew(name, quantity, description);
        items.add(item);
        calculateToTotalItems();

        return item.getId();
    }

    public void removeItem(UUID itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
        calculateToTotalItems();

    }

    public void clearItems() {
        items.clear();
        calculateToTotalItems();
    }

    public void changeItemQuantity(UUID itemId, int quantity) {
        Item item = getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst().orElseThrow();
        item.setQuantity(quantity);
        calculateToTotalItems();
    }

    public void editPreparationDetails(PreparationDetails details) {
        verifyIsDraft();

        setSender(details.getSender());
        setRecipient(details.getRecipient());
        setCourierPayout(details.getCourierPayout());
        setDistanceFee(details.getDistanceFee());
        setExpectedDeliveryAt(OffsetDateTime.now().plus(details.getExpectedDeliveryTime()));
        setTotalCost(this.getDistanceFee().add(this.getCourierPayout()));
    }

    public void place() {
        verifyCanBePlaced();
        this.setStatus(DeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());
    }

    public void pickUp(UUID courierId) {
        this.setStatus(DeliveryStatus.IN_TRANSIT);
        this.setCourierId(courierId);
        this.setAssignedAt(OffsetDateTime.now());
    }

    public void markAsDelivery() {
        this.setStatus(DeliveryStatus.DELIVERED);
        this.setFulfilledAt(OffsetDateTime.now());
    }

    private void verifyCanBePlaced() {

        verifyIsDraft();
        if (!isReadyForPlacement())
            throw new DomainException("A entrega não pode ser solicitada. Remetente, destinatário ou custo total não informado.");


    }


    private void verifyCanEdit() {
        verifyIsDraft();
    }

    private boolean isReadyForPlacement() {
        return this.getSender() != null
                && this.getRecipient() != null
                && this.getTotalCost() != null;
    }

    private void calculateToTotalItems() {
        int totalItems = getItems().stream().mapToInt(Item::getQuantity).sum();
        setTotalItems(totalItems);
    }

    private void verifyIsDraft() {
        if (!DeliveryStatus.DRAFT.equals(getStatus()))
            throw new DomainException("A entrega deve estar em rascunho para realizar esta operação.");
    }

}
