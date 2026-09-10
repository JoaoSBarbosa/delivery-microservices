package com.barbosacode.delivery.msdelivery.tracking.api.controllers;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.DeliveryCourierRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.DeliveryRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryCheckpointService;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryPreparationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    private final DeliveryPreparationService deliveryService;
    private final DeliveryCheckpointService deliveryCheckpointService;

    public DeliveryController(DeliveryPreparationService deliveryService, DeliveryCheckpointService deliveryCheckpointService) {
        this.deliveryService = deliveryService;
        this.deliveryCheckpointService = deliveryCheckpointService;
    }


    @GetMapping
    public ResponseEntity<Page<DeliveryResponse>> getDeliveries(@PageableDefault Pageable pageable) {
        Page<DeliveryResponse> deliveries = deliveryService.getAll(pageable);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> getDelivery(@PathVariable UUID deliveryId) {
        DeliveryResponse deliveryResponse = deliveryService.getById(deliveryId);
        return ResponseEntity.ok(deliveryResponse);
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> draft(@Valid @RequestBody DeliveryRequest deliveryDraft) {
        DeliveryResponse response = deliveryService.draft(deliveryDraft);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/{deliveryId}/pickups")
    public ResponseEntity<DeliveryResponse> pickup(@PathVariable UUID deliveryId, @Valid @RequestBody DeliveryCourierRequest pickupRequest) {
        DeliveryResponse response = deliveryCheckpointService.pickup(deliveryId, pickupRequest.getCourierId());
        return ResponseEntity.ok(response);


    }

    @PostMapping("/{deliveryId}/placement")
    public ResponseEntity<DeliveryResponse> place(@PathVariable UUID deliveryId) {
        DeliveryResponse response = deliveryCheckpointService.place(deliveryId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{deliveryId}/completion")
    public ResponseEntity<DeliveryResponse> complete(@PathVariable UUID deliveryId) {
        DeliveryResponse response = deliveryCheckpointService.complete(deliveryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> update(
            @PathVariable String deliveryId,
            @Valid @RequestBody DeliveryRequest deliveryDraft
    ) {
        DeliveryResponse response = deliveryService.update(UUID.fromString(deliveryId), deliveryDraft);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<Void> delete(@PathVariable UUID deliveryId) {
        deliveryService.delete(deliveryId);
        return ResponseEntity.noContent().build();
    }
}




