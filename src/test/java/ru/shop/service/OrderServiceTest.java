package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.shop.exception.BadOrderCountException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    private final OrderRepository orderRepository = Mockito.mock();
    private final OrderService orderService = new OrderService(orderRepository);

    @Test
    public void shouldAddOrder() {
        // given
        var customer = new Customer(UUID.randomUUID(), "customerName", "customerPhone", 11);
        var product = new Product(UUID.randomUUID(), "productName", 10, ProductType.GOOD);

        // when
        orderService.add(customer, product, 10);

        // then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.captor();

        Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();

        Assertions.assertEquals(10, savedOrder.getCount());
        Assertions.assertEquals(customer.getId(), savedOrder.getCustomerId());
        Assertions.assertEquals(product.getId(), savedOrder.getProductId());
    }

    @DisplayName("Ошибка, если не правильно задано количество товара при добавлении заказа")
    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    public void shouldThrowBadOrderCountException(Integer count) {
        // given
        var customer = new Customer(UUID.randomUUID(), "customerName", "customerPhone", 11);
        var product = new Product(UUID.randomUUID(), "productName", 10, ProductType.GOOD);

        // then
        Assertions.assertThrows(
                BadOrderCountException.class,
                () -> orderService.add(customer, product, count)
        );
    }

    @Test
    public void shouldFindCustomerOrders() {
        // given
        var customer = new Customer(UUID.randomUUID(), "customerName", "customerPhone", 11);
        var firstOrderId = UUID.randomUUID();
        var secondOrderId = UUID.randomUUID();

        Mockito.when(orderRepository.findAll()).thenReturn(
                List.of(
                        new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 2, 10),
                        new Order(firstOrderId, customer.getId(), UUID.randomUUID(), 2, 10),
                        new Order(secondOrderId, customer.getId(), UUID.randomUUID(), 2, 20)
                )
        );

        // when
        List<Order> customerOrders = orderService.findByCustomer(customer);

        // then
        assertThat(customerOrders)
                .hasSize(2)
                .extracting(Order::getId)
                .containsExactly(firstOrderId, secondOrderId);
    }

    @Test
    public void shouldCalculateCustomerTotalOrdersAmount() {
        // given
        var customer = new Customer(UUID.randomUUID(), "customerName", "customerPhone", 11);
        var firstOrderId = UUID.randomUUID();
        var secondOrderId = UUID.randomUUID();

        Mockito.when(orderRepository.findAll()).thenReturn(
                List.of(
                        new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 2, 10),
                        new Order(firstOrderId, customer.getId(), UUID.randomUUID(), 2, 10),
                        new Order(secondOrderId, customer.getId(), UUID.randomUUID(), 2, 20)
                )
        );

        // when
        long totalCustomerAmount = orderService.getTotalCustomerAmount(customer);

        // then
        assertThat(totalCustomerAmount);
    }
}