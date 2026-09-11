package com.barbosacode.delivery.mscourier.domain.services.impl;

import com.barbosacode.delivery.mscourier.api.dto.request.CourierRequest;
import com.barbosacode.delivery.mscourier.api.dto.response.CourierResponse;
import com.barbosacode.delivery.mscourier.domain.exceptions.CourierNotFoundException;
import com.barbosacode.delivery.mscourier.domain.exceptions.DomainException;
import com.barbosacode.delivery.mscourier.domain.mappers.CourierMapper;
import com.barbosacode.delivery.mscourier.domain.model.Courier;
import com.barbosacode.delivery.mscourier.domain.repository.CourierRepository;
import com.barbosacode.delivery.mscourier.domain.services.CourierRegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service

public class CourierRegistrationServiceImpl implements CourierRegistrationService {
    private final CourierRepository courierRepository;

    public CourierRegistrationServiceImpl(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    @Transactional
    public CourierResponse create(CourierRequest courierRequest) {
        Courier courier = Courier.brandNew(courierRequest.getCourierName(), courierRequest.getPhoneNumber());

        courier = courierRepository.saveAndFlush(courier);
        return CourierMapper.toResponse(courier);
    }

    @Override
    @Transactional
    public CourierResponse assignDeliveryToCourier(UUID deliveryId) {
        return null;
    }

    @Override
    @Transactional
    public CourierResponse fulfill(UUID deliveryId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CourierResponse findCourierById(UUID courierId) {

        Courier entity = courierRepository.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException("Entregador não encontrado para o ID: " + courierId));

        return CourierMapper.toResponse(entity);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourierResponse> findCouriers(Pageable pageRequest) {

        Page<Courier> couriers = courierRepository.findAll(pageRequest);

        return couriers.map(CourierMapper::toResponse);
    }

    @Override
    @Transactional
    public CourierResponse update(UUID courierId, CourierRequest courierRequest) {
        validateCourierId(courierId);
        validateRequest(courierRequest);


        Courier courier = courierRepository.findById(courierId).orElseThrow(() -> new CourierNotFoundException("Entregador não encontrado para o ID: " + courierId));
        courier.update(courierRequest.getCourierName(), courierRequest.getPhoneNumber());
        courier = courierRepository.saveAndFlush(courier);

        return CourierMapper.toResponse(courier);

    }

    @Override
    public void delete(UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(() -> new CourierNotFoundException("Entregador não encontrado para o ID: " + courierId));

        courierRepository.delete(courier);
    }


    private void validateCourierId(UUID courierId) {

        if (courierId == null) throw new DomainException("ID do entregador não pode ser nulo");

    }


    private void validateRequest(CourierRequest courierRequest) {
        if (courierRequest.getCourierName() == null || courierRequest.getCourierName().isBlank()) {
            throw new DomainException("Nome do entregador não pode ser nulo ou vazio");
        }

        if (courierRequest.getPhoneNumber() == null || courierRequest.getPhoneNumber().isBlank()) {
            throw new DomainException("Número de telefone do entregador não pode ser nulo ou vazio");
        }
    }

}
