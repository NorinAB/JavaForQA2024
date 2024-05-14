package ru.shop.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @MockBean
    CustomerRepository customerRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void shouldGetCustomers() {
        // given
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(new Customer()));

        // then
        when()
                .get(getRootUrl() + "/customer")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Customer[].class);
    }

    @Test
    public void shouldSaveCustomer() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("id", UUID.randomUUID().toString());
        request.put("name", "name1");
        request.put("phone", "phone");
        request.put("age", "10");

        // then
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(getRootUrl() + "/customer")
                .then()
                .statusCode(HttpStatus.OK.value());

        Mockito.verify(customerRepository).save(any());
    }

    @Test
    public void shouldThrowBadRequestException() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("id", UUID.randomUUID().toString());
        request.put("phone", "phone");
        request.put("age", "10");

        // then
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(getRootUrl() + "/customer")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        Mockito.verify(customerRepository, Mockito.never()).save(any());
    }

    @Test
    public void shouldGetCustomerById() {
        // given
        var customerId = UUID.randomUUID();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(
                        new Customer(customerId, "name", "phone", 11)
                )
        );

        // then
        Customer customer = when()
                .get(getRootUrl() + "/customer/" + customerId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Customer.class);

        assertThat(customer)
                .isNotNull()
                .returns(customerId, Customer::getId)
                .returns("name", Customer::getName)
                .returns("phone", Customer::getPhone);
    }
}