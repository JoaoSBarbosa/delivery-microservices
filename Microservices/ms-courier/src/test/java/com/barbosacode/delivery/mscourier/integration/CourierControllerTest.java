package com.barbosacode.delivery.mscourier.integration;


import com.barbosacode.delivery.mscourier.integration.dto.request.CourierRequest;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourierControllerTest {


    private static final Faker faker = new Faker(Locale.of("pt-BR"));
    @LocalServerPort
    private int port;

    @BeforeEach
    void configureRestAssured() {
        RestAssured.basePath = "/api/v1/couriers";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Deve retornar 201 ao cadastrar entregador")
    public void shouldReturn201WhenCreatedCourier() {
        String courierName = faker.name().fullName();
        String courierPhone = faker.phoneNumber().phoneNumber();

        String requestBody = """
                    {
                       "courierName": "%s",
                       "phoneNumber": "%s"
                    }
                """.formatted(courierName, courierPhone);

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("courierId", notNullValue())
                .body("courierName", equalTo(courierName))
                .body("phoneNumber", equalTo(courierPhone));

    }

    private CourierRequest buildRequest() {
        CourierRequest request = new CourierRequest();
        request.setCourierName(faker.name().fullName());
        request.setPhoneNumber(faker.phoneNumber().phoneNumber());
        return request;
    }

    @Test
    @DisplayName("Deve retornar 400 quando nome do entregador não for informado")
    void shouldReturn400WhenCourierNameIsBlank() {
        CourierRequest request = buildRequest();
        String requestBody = """
                     "courierName": "",
                     "phoneNumber": "%s"
                """.formatted(request.getPhoneNumber());

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar 400 quando o telefone do entregador não for informado")
    void shouldReturn400WhenCourierPhoneIsBlank() {
        CourierRequest request = buildRequest();

        String requestBody = """
                             {
                             "courierName": "%s",
                             "courierPhone": "",
                }
                """.formatted(request.getCourierName());

        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when().post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
        ;

    }


    @Test
    @DisplayName("Deve retornar 200 ao buscar entregadores")
    void shouldFindCouriers() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", notNullValue())
                .body("pageable", notNullValue());
    }

    @Test
    @DisplayName("Deve retornar 200 ao buscar entregador por ID")
    void shouldFindCourierById() {
        CourierRequest request = buildRequest();

        String courierId = createCourier(request);
        given()
                .accept(ContentType.JSON)
                .when().get("/{courierId}", courierId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("courierId", equalTo(courierId))
                .body("courierName", equalTo(request.getCourierName()))
                .body("phoneNumber", equalTo(request.getPhoneNumber()));

    }

    @Test
    @DisplayName("Deve retornar 200 ao atualizar entregador")
    void shouldUpdateCourier() {
        CourierRequest request = buildRequest();

        String courierId = createCourier(request);
        String courierNewName = "Marcos Barbosa";
        String courierNewNumber = "11998745822";

        String requestBody = """
                {
                    "courierName": "%s",
                    "phoneNumber": "%s"
                }
                """.formatted(courierNewName, courierNewNumber);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put("/{courierId}", courierId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("courierId", equalTo(courierId))
                .body("courierName", equalTo(courierNewName))
                .body("phoneNumber", equalTo(courierNewNumber));

    }

    @Test
    @DisplayName("Deve retornar 204 ao excluir entregador")
    void shouldDeleteCourier() {
        String courierId = createCourier(buildRequest());

        given().when().delete("/{courierId}", courierId).then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    private String createCourier(CourierRequest request) {

        String requestBody = """
                {
                    "courierName": "%s",
                    "phoneNumber": "%s"
                }
                """.formatted(request.getCourierName(), request.getPhoneNumber());

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("courierId");
    }
}
