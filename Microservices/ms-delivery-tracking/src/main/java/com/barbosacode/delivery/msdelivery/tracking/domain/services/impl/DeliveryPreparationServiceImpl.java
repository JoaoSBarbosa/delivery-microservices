package com.barbosacode.delivery.msdelivery.tracking.domain.services.impl;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.DeliveryRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;
import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainException;
import com.barbosacode.delivery.msdelivery.tracking.domain.mappers.DeliveryMapper;
import com.barbosacode.delivery.msdelivery.tracking.domain.model.Delivery;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryPreparationService;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.PreparationDetails;
import com.barbosacode.delivery.msdelivery.tracking.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
public class DeliveryPreparationServiceImpl implements DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryPreparationServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    @Transactional
    public DeliveryResponse draft(DeliveryRequest deliveryRequest) {

        try {
            Delivery draftDelivery = Delivery.draft();
            handlePreparation(deliveryRequest, draftDelivery);

            Delivery savedDelivery = deliveryRepository.saveAndFlush(draftDelivery);

            return DeliveryMapper.toResponse(savedDelivery);
        } catch (Exception ex) {
            throw new DomainException(ex.getMessage());
        }
    }


    @Override
    public DeliveryResponse getById(UUID deliveryId) {

        validateDeliveryId(deliveryId);
        Delivery deliver = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException("Entrega de ID " + deliveryId + " não encontrado."));
        return DeliveryMapper.toResponse(deliver);
    }

    @Override
    public DeliveryResponse update(UUID deliveryId, DeliveryRequest deliveryRequest) {
        validateDeliveryId(deliveryId);
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException("Entrega de ID " + deliveryId + " não encontrado."));
        handlePreparation(deliveryRequest, delivery);
        return DeliveryMapper.toResponse(deliveryRepository.saveAndFlush(delivery));
    }

    @Override
    public void delete(UUID deliveryId) {
        validateDeliveryId(deliveryId);

        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException("Entrega de ID " + deliveryId + " não encontrado."));
        deliveryRepository.delete(delivery);
    }

    @Override
    public Page<DeliveryResponse> getAll(Pageable pageable) {

        Page<Delivery> deliveries = deliveryRepository.findAll(pageable);

        return deliveries.map(DeliveryMapper::toResponse);
    }

    private void handlePreparation(DeliveryRequest deliveryRequest, Delivery delivery) {
        if (deliveryRequest.getSender() == null) throw new DomainException("O remetente não pode ser nulo");
        if (deliveryRequest.getRecipient() == null) throw new DomainException("O destinatário não pode ser nulo");
        if (deliveryRequest.getItems() == null || deliveryRequest.getItems().isEmpty())
            throw new DomainException("A lista de itens não pode ser nula ou vazia");

        PreparationDetails details = PreparationDetails.builder()
                .sender(DeliveryMapper.toContactPoint(deliveryRequest.getSender()))
                .recipient(DeliveryMapper.toContactPoint(deliveryRequest.getRecipient()))
                .distanceFee(new BigDecimal("15.0"))
                .courierPayout(new BigDecimal("100.0"))
                .expectedDeliveryTime(Duration.ofHours(3))
                .build();
        delivery.editPreparationDetails(details);
        delivery.clearItems();
        for (var item : deliveryRequest.getItems())
            delivery.addItem(item.getName(), item.getQuantity(), item.getDescription());

    }

    private void validateDeliveryId(UUID deliveryId) {
        if (deliveryId == null)
            throw new DomainException("O ID da entrega não pode ser nulo");

    }
}
