package ru.shop.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductReturnRepository;
import ru.shop.service.OrderService;

import java.time.LocalDate;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductReturnControllerTest {
    @MockBean
    ProductReturnRepository productReturnRepository;
    @MockBean
    OrderService orderService;
    Map<String, String> request;
    @LocalServerPort
    private int port;
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    @Test
    public void shouldSaveProductReturn() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, 3);
        Mockito.when(orderService.getById(order.getId())).thenReturn(order);
        request = new HashMap<>();
        request.put("orderId", order.getId().toString());
        request.put("count", String.valueOf(5L));
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(getRootUrl() + "/product_return")
                .then()
                .statusCode(HttpStatus.OK.value());
        Mockito.verify(productReturnRepository).save(any());
    }
    @Test
    public void shouldGetAllProductReturn() {
        Mockito.when(productReturnRepository.findAll()).thenReturn(List.of(new ProductReturn()));
        when()
                .get(getRootUrl() + "/product_return")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductReturn[].class);
    }
    @Test
    public void shouldGetProductReturn() {
        ProductReturn productReturn = new ProductReturn(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), 10L);
          Mockito.when(productReturnRepository.findById(productReturn.getId())).thenReturn(Optional.of(productReturn));
        when()
                .get(getRootUrl() + "/product_return/" + productReturn.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ProductReturn.class)
                .getQuantity().equals(productReturn.getQuantity());
    }
}
