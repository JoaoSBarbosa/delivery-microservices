package com.barbosacode.delivery.msdelivery.tracking.domain.services.impl;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;
import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainException;
import com.barbosacode.delivery.msdelivery.tracking.domain.mappers.DeliveryMapper;
import com.barbosacode.delivery.msdelivery.tracking.domain.model.Delivery;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryCheckpointService;
import com.barbosacode.delivery.msdelivery.tracking.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class DeliveryCheckpointServiceImpl implements DeliveryCheckpointService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryCheckpointServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public DeliveryResponse pickup(UUID deliveryId, UUID courierId) {

        Delivery delivery = getDeliveryById(deliveryId);

        delivery.pickUp(courierId);
        deliveryRepository.saveAndFlush(delivery);
        return DeliveryMapper.toResponse(delivery);


    }

    @Override
    public DeliveryResponse place(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);
        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
        return DeliveryMapper.toResponse(delivery);
    }

    @Override
    public DeliveryResponse complete(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);
        delivery.markAsDelivery();
        deliveryRepository.saveAndFlush(delivery);
        return DeliveryMapper.toResponse(delivery);


    }

    private Delivery getDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException("Entrega não encontrada para o ID: " + deliveryId));
    }
}
