package com.barbosacode.delivery.mscourier.api.controller;

import com.barbosacode.delivery.mscourier.api.dto.request.CourierRequest;
import com.barbosacode.delivery.mscourier.api.dto.response.CourierResponse;
import com.barbosacode.delivery.mscourier.domain.services.CourierRegistrationService;
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
@RequestMapping("/api/v1/couriers")
public class CourierController {

    private final CourierRegistrationService courierRegistrationService;

    public CourierController(CourierRegistrationService courierRegistrationService) {
        this.courierRegistrationService = courierRegistrationService;
    }

    @GetMapping
    public ResponseEntity<Page<CourierResponse>> findCouriers(@PageableDefault Pageable pageable) {
        Page<CourierResponse> couriers = courierRegistrationService.findCouriers(pageable);
        return ResponseEntity.ok(couriers);
    }

    @GetMapping("/{courierId}")
    public ResponseEntity<CourierResponse> findCourierById(@PathVariable UUID courierId) {
        CourierResponse courier = courierRegistrationService.findCourierById(courierId);
        return ResponseEntity.ok(courier);
    }

    @PostMapping
    public ResponseEntity<CourierResponse> create(@Valid @RequestBody CourierRequest courierRequest) {
        CourierResponse courier = courierRegistrationService.create(courierRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(courier.courierId()).toUri();

        return ResponseEntity.created(uri).body(courier);

    }

    @PutMapping("/{courierId}")
    public ResponseEntity<CourierResponse> update(@PathVariable UUID courierId, @Valid @RequestBody CourierRequest courierRequest) {
        CourierResponse courier = courierRegistrationService.update(courierId, courierRequest);
        return ResponseEntity.ok(courier);
    }

    @DeleteMapping("/{courierId}")
    public ResponseEntity<Void> delete(@PathVariable UUID courierId) {
        courierRegistrationService.delete(courierId);
        return ResponseEntity.noContent().build();
    }
}
