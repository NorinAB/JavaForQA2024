package ru.shop.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.*;

import static io.restassured.RestAssured.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
    @MockBean
    CustomerRepository customerRepository;
    Map<String, String> request;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }


    @Test
    public void shouldSaveCustomer() {
        // given
        request = new HashMap<>();
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
    public void shouldGetCustomer() {
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
    public void shouldGetByIdCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "name2", "123", 10L);
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when()
                .get(getRootUrl() + "/customer/" + customer.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response().body()
                .as(Customer.class)
                .getName().equals(customer.getName());

    }


}