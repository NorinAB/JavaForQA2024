package ru.shop.service;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.shop.exception.BadOrderCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderServiceTest {
    private final OrderRepository repository = Mockito.mock();
    private final OrderService orderService = new OrderService(repository);

    @Test
    public void shouldAddOrder() {
        var product = new Product(UUID.randomUUID(), "p1", 10, ProductType.GOOD);
        var customer = new Customer(UUID.randomUUID(), "name", "1233", 12);
        orderService.add(customer, product, 10L);
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(repository, Mockito.times(1)).save(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();
        Assertions.assertEquals(10, savedOrder.getCount());
        Assertions.assertEquals(customer.getId(), savedOrder.getCustomerId());
        Assertions.assertEquals(product.getId(), savedOrder.getProductId());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    public void shouldThrowWhenCountIsBad(Integer count) {
        var product = new Product(UUID.randomUUID(), "p1", 10, ProductType.GOOD);
        var customer = new Customer(UUID.randomUUID(), "name", "1233", 12);
//        orderService.add(customer, product, -10L);
        // then
        Assertions.assertThrows(
                BadOrderCountException.class,
                () -> orderService.add(customer, product, count)
        );
    }
    @Test
    public void shouldFindByCustomer() {
        var product = new Product(UUID.randomUUID(), "p1", 10, ProductType.GOOD);
        var customer = new Customer(UUID.randomUUID(), "name", "1233", 12);
        var customer1 = new Customer(UUID.randomUUID(), "name1", "3211", 12);
        var firstOrderId = UUID.randomUUID();
        var secondOrderId = UUID.randomUUID();
        Order order = new Order(firstOrderId, customer.getId(), UUID.randomUUID(), 2, 10);
        Order order1 = new Order(secondOrderId, customer.getId(), UUID.randomUUID(), 2, 20);
        Mockito.when(repository.findAll()).thenReturn(List.of(
                new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 2, 10),
                order, order1
                ));
        List<Order> customerOrders = orderService.findByCustomer(customer);
        Assertions.assertEquals(2, customerOrders.size());
        assertThat(customerOrders).hasSize(2).extracting(Order::getId).containsExactly(firstOrderId, secondOrderId);

        Assertions.assertEquals(order, customerOrders.get(0));
        Assertions.assertEquals(order1, customerOrders.get(1));

    }
    @Test
    public void TotalCustomerAmount() {
        var product = new Product(UUID.randomUUID(), "p1", 10, ProductType.GOOD);
        var customer = new Customer(UUID.randomUUID(), "name", "1233", 12);
        var customer1 = new Customer(UUID.randomUUID(), "name1", "3211", 12);
        var firstOrderId = UUID.randomUUID();
        var secondOrderId = UUID.randomUUID();
        Order order = new Order(firstOrderId, customer.getId(), product.getId(), 1, 10);
        Order order1 = new Order(secondOrderId, customer.getId(), product.getId(), 2, 20);
        Order order2 = new Order(secondOrderId, customer1.getId(), product.getId(), 2, 30);
        Mockito.when(repository.findAll()).thenReturn(List.of(
                new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 2, 10),
                order, order1, order2
        ));
        long count = orderService.getTotalCustomerAmount(customer);
        Assertions.assertEquals(30, count);
//        assertThat(customerOrders).hasSize(2).extracting(Order::getId).containsExactly(firstOrderId, secondOrderId);
//
//        Assertions.assertEquals(order, customerOrders.get(0));
//        Assertions.assertEquals(order1, customerOrders.get(1));
    }
    @Test
    public void TotalCustomerAmountNull() {}
}
