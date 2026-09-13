package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import com.barbosacode.delivery.msdelivery.tracking.domain.enums.DeliveryStatus;
import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainException;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ItemDraft;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.PreparationDetails;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "sender_zip_code")),
            @AttributeOverride(name = "streetAddress", column = @Column(name = "sender_street_address")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "sender_street_number")),
            @AttributeOverride(name = "city", column = @Column(name = "sender_city")),
            @AttributeOverride(name = "state", column = @Column(name = "sender_state")),
            @AttributeOverride(name = "country", column = @Column(name = "sender_country")),
            @AttributeOverride(name = "complement", column = @Column(name = "sender_complement")),
            @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
            @AttributeOverride(name = "phoneNumber.countryCode", column = @Column(name = "sender_phone_country_code")),
            @AttributeOverride(name = "phoneNumber.value", column = @Column(name = "sender_phone_number"))
    })
    private ContactPoint sender;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "recipient_zip_code")),
            @AttributeOverride(name = "streetAddress", column = @Column(name = "recipient_street_address")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "recipient_street_number")),
            @AttributeOverride(name = "city", column = @Column(name = "recipient_city")),
            @AttributeOverride(name = "state", column = @Column(name = "recipient_state")),
            @AttributeOverride(name = "country", column = @Column(name = "recipient_country")),
            @AttributeOverride(name = "complement", column = @Column(name = "recipient_complement")),
            @AttributeOverride(name = "name", column = @Column(name = "recipient_name")),
            @AttributeOverride(name = "phoneNumber.value", column = @Column(name = "recipient_phone_number"))
    })
    private ContactPoint recipient;


    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();


    public static Delivery draft() {
        Delivery delivery = new Delivery();

        delivery.setId(UUID.randomUUID());
        delivery.setStatus(DeliveryStatus.DRAFT);
        delivery.setDistanceFee(BigDecimal.ZERO);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setCourierPayout(BigDecimal.ZERO);
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
        Item item = Item.brandNew(name, quantity, this, description);
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

    public void replaceItems(List<ItemDraft> items) {
        this.clearItems();

        items.forEach(item -> this.addItem(item.name(), item.quantity(), item.description()));
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
        changeStatusTo(DeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());
    }

    public void pickUp(UUID courierId) {
        changeStatusTo(DeliveryStatus.IN_TRANSIT);
        this.setCourierId(courierId);
        this.setAssignedAt(OffsetDateTime.now());
    }

    public void markAsDelivery() {
        changeStatusTo(DeliveryStatus.DELIVERED);
        this.setFulfilledAt(OffsetDateTime.now());
    }

    private void changeStatusTo(DeliveryStatus newStatus) {
        if (newStatus == null) throw new DomainException("O novo status da entrega não pode ser nulo.");
        if (this.getStatus().canNotChangeTo(newStatus))
            throw new DomainException("Transição de status inválida: de " + this.getStatus() + " para " + newStatus);

        this.setStatus(newStatus);
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
