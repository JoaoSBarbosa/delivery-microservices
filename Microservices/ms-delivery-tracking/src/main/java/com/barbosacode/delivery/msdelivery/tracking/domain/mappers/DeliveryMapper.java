package com.barbosacode.delivery.msdelivery.tracking.domain.mappers;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.ContactPointRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.DeliveryRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.ItemRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.PhoneNumberRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.ContactPointResponse;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.ItemResponse;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.PhoneNumberResponse;
import com.barbosacode.delivery.msdelivery.tracking.domain.model.Delivery;
import com.barbosacode.delivery.msdelivery.tracking.domain.model.Item;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.*;

import java.math.BigDecimal;
import java.util.List;

public final class DeliveryMapper {

    private DeliveryMapper() {
    }

    public static PreparationDetails toPreparationDetails(DeliveryRequest request) {
        return PreparationDetails.builder()
                .sender(toContactPoint(request.getSender()))
                .recipient(toContactPoint(request.getRecipient()))
                .distanceFee(null)
                .courierPayout(null)
                .expectedDeliveryTime(null)
                .build();
    }

    public static DeliveryResponse toResponse(Delivery entity) {
        return new DeliveryResponse(
                entity.getId(),
                entity.getCourierId(),
                entity.getPlacedAt(),
                entity.getAssignedAt(),
                entity.getExpectedDeliveryAt(),
                entity.getFulfilledAt(),
                entity.getDistanceFee(),
                entity.getCourierPayout(),
                entity.getTotalCost(),
                entity.getTotalItems(),
                entity.getStatus(),
                toContactPointResponse(entity.getSender()),
                toContactPointResponse(entity.getRecipient()),
                toItemResponseList(entity.getItems())
        );
    }

    public static ContactPointResponse toContactPointResponse(ContactPoint entity) {
        return new ContactPointResponse(
                entity.getZipCode(),
                entity.getStreetAddress(),
                entity.getStreetNumber(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getComplement(),
                entity.getName(),
                toPhoneNumberResponse(entity.getPhoneNumber())
        );
    }

    public static ContactPoint toContactPoint(ContactPointRequest request) {
        return ContactPoint.builder()
                .zipCode(request.getZipCode())
                .streetAddress(request.getStreetAddress())
                .streetNumber(request.getStreetNumber())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .complement(request.getComplement())
                .name(request.getName())
                .phoneNumber(toPhoneNumber(request.getPhoneNumber()))
                .build();
    }

    public static PhoneNumberResponse toPhoneNumberResponse(PhoneNumber entity) {
        return new PhoneNumberResponse(
                entity.getValue()
        );
    }

    public static PreparationDetails toPreparationDetails(ContactPoint sender,
                                                          ContactPoint recipient,
                                                          DeliveryEstimate estimate,
                                                          BigDecimal courierPayout,
                                                          BigDecimal distanceFee) {

        return PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(distanceFee)
                .courierPayout(courierPayout)
                .expectedDeliveryTime(estimate.getEstimatedTime())
                .build();
    }

    private static ItemResponse toItemResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getQuantity()
        );
    }


    private static PhoneNumber toPhoneNumber(PhoneNumberRequest request) {
        return PhoneNumber.builder()
                .value(request.getNumber())
                .build();
    }

    private static List<ItemResponse> toItemResponseList(List<Item> items) {
        return items.stream()
                .map(DeliveryMapper::toItemResponse)
                .toList();
    }

    public static List<ItemDraft> toItemDraftList(List<ItemRequest> itemRequests) {
        return itemRequests.stream()
                .map(request -> new ItemDraft(request.getName(), request.getQuantity(), request.getDescription()))
                .toList();
    }


}
