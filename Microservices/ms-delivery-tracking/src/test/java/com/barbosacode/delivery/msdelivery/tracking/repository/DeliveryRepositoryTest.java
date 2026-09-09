package com.barbosacode.delivery.msdelivery.tracking.repository;

import com.barbosacode.delivery.msdelivery.tracking.domain.model.Delivery;
import com.barbosacode.delivery.msdelivery.tracking.support.DeliveryTestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeliveryRepositoryTest {


    @Autowired
    private DeliveryRepository deliveryRepository;


    @Test
    @DisplayName("Deve salvar uma entrega no banco de dados")
    public void shouldSaveDeliveryInDatabase() {
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(DeliveryTestDataFactory.buildPreparationDetails());

        delivery.addItem("Playstation 6", 2, "Console de videogame de última geração");
        delivery.addItem("Xbox Series Z", 1, "Console de videogame de última geração");

        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertAll(
                () -> assertEquals(delivery.getId(), persistedDelivery.getId()),
                () -> assertEquals(delivery.getItems().size(), persistedDelivery.getItems().size())
        );
    }
}
