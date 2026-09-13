package com.barbosacode.delivery.msdelivery.tracking.domain.services.impl;

import com.barbosacode.delivery.msdelivery.tracking.api.dto.request.DeliveryRequest;
import com.barbosacode.delivery.msdelivery.tracking.api.dto.response.DeliveryResponse;
import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainException;
import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainNotFoudException;
import com.barbosacode.delivery.msdelivery.tracking.domain.mappers.DeliveryMapper;
import com.barbosacode.delivery.msdelivery.tracking.domain.model.Delivery;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.CourierPayoutCalculationService;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryPreparationService;
import com.barbosacode.delivery.msdelivery.tracking.domain.services.DeliveryTimeEstimationService;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.ContactPoint;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.DeliveryEstimate;
import com.barbosacode.delivery.msdelivery.tracking.domain.valueObject.PreparationDetails;
import com.barbosacode.delivery.msdelivery.tracking.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class DeliveryPreparationServiceImpl implements DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryTimeEstimationService deliveryTimeEstimationService;
    private final CourierPayoutCalculationService courierPayoutCalculationService;
    private final DeliveryFeeCalculationService deliveryFeeCalculationService;

    public DeliveryPreparationServiceImpl(
            DeliveryRepository deliveryRepository,
            DeliveryTimeEstimationService deliveryTimeEstimationService,
            CourierPayoutCalculationService courierPayoutCalculationService,
            DeliveryFeeCalculationService deliveryFeeCalculationService) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryTimeEstimationService = deliveryTimeEstimationService;
        this.courierPayoutCalculationService = courierPayoutCalculationService;
        this.deliveryFeeCalculationService = deliveryFeeCalculationService;
    }

    @Override
    @Transactional
    public DeliveryResponse draft(DeliveryRequest deliveryRequest) {
        Delivery draftDelivery = Delivery.draft();
        prepareDelivery(deliveryRequest, draftDelivery);
        Delivery savedDelivery = deliveryRepository.saveAndFlush(draftDelivery);
        return DeliveryMapper.toResponse(savedDelivery);

    }

    @Override
    public DeliveryResponse update(UUID deliveryId, DeliveryRequest deliveryRequest) {

        Delivery delivery = findDelivery(deliveryId);
        prepareDelivery(deliveryRequest, delivery);
        Delivery updateDelivery = deliveryRepository.save(delivery);

        return DeliveryMapper.toResponse(updateDelivery);
    }

    @Override
    public DeliveryResponse getById(UUID deliveryId) {
        Delivery delivery = findDelivery(deliveryId);

        return DeliveryMapper.toResponse(delivery);
    }


    @Override
    public void delete(UUID deliveryId) {
        Delivery delivery = findDelivery(deliveryId);
        deliveryRepository.delete(delivery);
    }

    @Override
    public Page<DeliveryResponse> getAll(Pageable pageable) {

        return deliveryRepository.findAll(pageable).map(DeliveryMapper::toResponse);
    }


    private Delivery findDelivery(UUID deliveryId) {
        validateDeliveryId(deliveryId);
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainNotFoudException("Entrega de ID " + deliveryId + " não encontrada."));
    }

    private void prepareDelivery(DeliveryRequest deliveryRequest, Delivery delivery) {

        ContactPoint sender = DeliveryMapper.toContactPoint(deliveryRequest.getSender());
        ContactPoint recipient = DeliveryMapper.toContactPoint(deliveryRequest.getRecipient());

        DeliveryEstimate estimate = deliveryTimeEstimationService.estimate(sender, recipient);
        BigDecimal courierPayout = courierPayoutCalculationService.calculatePayout(estimate.getDistanceInKm());
        BigDecimal distanceFee = deliveryFeeCalculationService.calculateFee(estimate.getDistanceInKm());

        PreparationDetails details = DeliveryMapper.toPreparationDetails(sender, recipient, estimate, courierPayout, distanceFee);


        delivery.editPreparationDetails(details);


        delivery.replaceItems(DeliveryMapper.toItemDraftList(deliveryRequest.getItems()));
        

    }

    private BigDecimal calculateFee(Double distanceInKm) {
        return new BigDecimal("3").multiply(new BigDecimal(distanceInKm)).setScale(2, RoundingMode.HALF_EVEN);
    }

    private void validateDeliveryId(UUID deliveryId) {
        if (deliveryId == null)
            throw new DomainException("O ID da entrega não pode ser nulo");

    }
}
