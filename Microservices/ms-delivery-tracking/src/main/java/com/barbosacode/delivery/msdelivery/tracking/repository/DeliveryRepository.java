package com.barbosacode.delivery.msdelivery.tracking.domain.repository;

import com.barbosacode.delivery.msdelivery.tracking.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
