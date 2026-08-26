package com.barbosacode.delivery.msdelivery.tracking.domain.Enums;

import com.barbosacode.delivery.msdelivery.tracking.domain.enums.DeliveryStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeliveryStatusTest {


    @Test
    @DisplayName("Deve permitir mudança de RASCUNHO para AGUARDANDO_ENTREGADOR")
    void shouldAllowTransitionFromDraftToWaitingForCourier() {
        assertTrue(DeliveryStatus.DRAFT.canChangeTo(DeliveryStatus.WAITING_FOR_COURIER));
    }

    @Test
    @DisplayName("Deve permitir mudança de AGUARDANDO_ENTREGADOR para EM_TRÂNSITO")
    void shouldAllowTransitionFromWaitingForCourierToInTransit() {
        assertTrue(
                DeliveryStatus.WAITING_FOR_COURIER.canChangeTo(DeliveryStatus.IN_TRANSIT)
        );
    }


    @Test
    @DisplayName("Deve permitir mudança EM_TRÂNSITO para ENTREGUE")
    void shouldAllowTransitionFromInTransitToDelivered() {
        assertTrue(DeliveryStatus.IN_TRANSIT.canChangeTo(DeliveryStatus.DELIVERED));
    }

    @Test
    @DisplayName("Não deve permitir mudança de RASCUNHO para EM_TRÂNSITO")
    void shouldNotAllowTransitionFromDraftToInTransit() {
        assertTrue(DeliveryStatus.DRAFT.canNotChangeTo(DeliveryStatus.IN_TRANSIT));
    }

    @Test
    @DisplayName("Não deve permitir mudança de RASCUNHO para ENTREGUE")
    void shouldNotAllowTransitionFromDraftToDelivered() {
        assertTrue(
                DeliveryStatus.DRAFT.canNotChangeTo(DeliveryStatus.DELIVERED)
        );
    }

    @Test
    @DisplayName("Não deve permitir mudança de AGUARDANDO_ENTREGADOR para ENTREGUE")
    void shouldNotAllowTransitionFromWaitingForCourierToDelivered() {

        assertTrue(DeliveryStatus.WAITING_FOR_COURIER.canNotChangeTo(DeliveryStatus.DELIVERED));

    }

    @Test
    @DisplayName("Não deve permitir voltar de EM_TRÂNSITO para RASCUNHO")
    void shouldNotAllowTransitionFromInTransitToDraft() {
        assertTrue(DeliveryStatus.IN_TRANSIT.canNotChangeTo(DeliveryStatus.DRAFT));
    }

    @Test
    @DisplayName("Não deve permitir voltar de ENTREGUE para EM_TRÂNSITO")
    void shouldNotAllowTransitionFromDeliveredToInTransit() {
        assertTrue(DeliveryStatus.DELIVERED.canNotChangeTo(DeliveryStatus.IN_TRANSIT));
    }


    @Test
    @DisplayName("Não deve permitir voltar de ENTREGUE para RASCUNHO")
    void shouldNotAllowTransitionFromDeliveredToDraft() {
        assertTrue(DeliveryStatus.DELIVERED.canNotChangeTo(DeliveryStatus.DRAFT));
    }

    @Test
    @DisplayName("Não deve permitir que uma entrega ENTREGUE tenha seu status alterado")
    void shouldNotAllowAnyTransitionFromDelivered() {

        assertAll(
                () -> assertTrue(DeliveryStatus.DELIVERED.canNotChangeTo(DeliveryStatus.DRAFT)),
                () -> assertTrue(DeliveryStatus.DELIVERED.canNotChangeTo(DeliveryStatus.WAITING_FOR_COURIER)),
                () -> assertTrue(DeliveryStatus.DELIVERED.canNotChangeTo(DeliveryStatus.IN_TRANSIT))
        );
    }

    @Test
    @DisplayName("Não deve permitir mudança para o mesmo status")
    void shouldNotAllowTransitionToSameStatus() {
        assertAll(
                () -> assertTrue(DeliveryStatus.DRAFT.canNotChangeTo(DeliveryStatus.DRAFT)),
                () -> assertTrue(DeliveryStatus.WAITING_FOR_COURIER.canNotChangeTo(DeliveryStatus.WAITING_FOR_COURIER)),
                () -> assertTrue(DeliveryStatus.IN_TRANSIT.canNotChangeTo(DeliveryStatus.IN_TRANSIT)),
                () -> assertTrue(DeliveryStatus.DELIVERED.canNotChangeTo(DeliveryStatus.DELIVERED))
        );
    }
}
