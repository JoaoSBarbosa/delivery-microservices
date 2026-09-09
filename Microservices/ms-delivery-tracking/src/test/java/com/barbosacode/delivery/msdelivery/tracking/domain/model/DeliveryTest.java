package com.barbosacode.delivery.msdelivery.tracking.domain.model;

import com.barbosacode.delivery.msdelivery.tracking.domain.enums.DeliveryStatus;
import com.barbosacode.delivery.msdelivery.tracking.domain.exceptions.DomainException;
import com.barbosacode.delivery.msdelivery.tracking.support.DeliveryTestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTest {


    @Test
    @DisplayName("Deve alterar o status para AGUARDANDO_ENTREGADOR ao realizar a entrega")
    public void shouldChangeStatusToPlaced() {
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(DeliveryTestDataFactory.buildPreparationDetails());
        delivery.place();
        assertAll(
                () -> assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus()),
                () -> assertNotNull(delivery.getPlacedAt())
        );
    }

    @Test
    @DisplayName("Deve lançar DomainException ao tentar colocar a entrega sem detalhes de preparação")
    public void shouldNotPlaceDeliveryWithoutPreparationDetails() {
        Delivery deliver = Delivery.draft();

        DomainException exception = assertThrows(DomainException.class, deliver::place);
        assertEquals("A entrega não pode ser solicitada. Remetente, destinatário ou custo total não informado.", exception.getMessage());
    }
}
