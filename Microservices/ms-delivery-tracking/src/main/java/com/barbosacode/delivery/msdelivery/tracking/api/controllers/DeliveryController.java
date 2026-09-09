package com.barbosacode.delivery.msdelivery.tracking.api.controllers;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.DeliveryRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }


    @GetMapping
    public ResponseEntity<Page<DeliveryResponse>> getDeliveries(Pageable pageable) {
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

    @PutMapping("/{deliveryId}")
    public ResponseEntity<DeliveryResponse> update(
            @PathVariable String deliveryId,
            @Valid @RequestBody DeliveryRequest deliveryDraft
    ) {
        DeliveryResponse response = deliveryService.update(UUID.fromString(deliveryId), deliveryDraft);
        return ResponseEntity.ok(response);
    }
}




