package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private final OrderRepository orderRepository = Mockito.mock();
    private final OrderService orderService = new OrderService(orderRepository);


    @Test
    public void shouldAddOrder() {
        //given
        var customer = new Customer(UUID.randomUUID(), "name", "phone", 11);
        var product = new Product(UUID.randomUUID(), "name", 10, ProductType.GOOD);
        //when
        orderService.add(customer, product, 10);
        //then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());
        Order saveOrder = orderArgumentCaptor.getValue();
        Assertions.assertEquals(10, saveOrder.getCount());
        Assertions.assertEquals(customer.getId(), saveOrder.getCustomerId());
        Assertions.assertEquals(product.getId(), saveOrder.getProductId());
    }

    @ParameterizedTest
    @ValueSource(ints = {0,-1, Integer.MIN_VALUE})
    public void shouldThrowBadOrderCountExeption() {

        var customer = new Customer(UUID.randomUUID(), "name", "phone", 11);
        var product = new Product(UUID.randomUUID(), "name", 10, ProductType.GOOD);

        //given
        //when

        //then
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> orderService.add(customer,product,10)
        );
    }

    @Test
    public void shouldfindByCustomerOrder() {
        //given
        var customer = new Customer(UUID.randomUUID(), "name", "phone", 11);
        var firstOrderId = UUID.randomUUID();
        var secondOrderId = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(
                List.of(
                        new Order(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),2,10),
                        new Order(firstOrderId, customer.getId(),UUID.randomUUID(),2,10),
                        new Order(secondOrderId,customer.getId(),UUID.randomUUID(),2,20)
                )
        );
        //when
        List<Order> customerOrders = orderService.findByCustomer(customer);
        //then
       Assertions.assertEquals(2,customerOrders.size());
       assertThat(customerOrders)
               .hasSize(2)
               .extracting(Order::getId)
               .containsExactly(firstOrderId,secondOrderId);
    }

    @Test
    public void shoulgdetTotalCustomerAmount() {
        //given
        var customer = new Customer(UUID.randomUUID(), "name", "phone", 11);
        var firstOrderId = UUID.randomUUID();
        var secondOrderId = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(
                List.of(
                        new Order(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),2,10),
                        new Order(firstOrderId, customer.getId(),UUID.randomUUID(),2,10),
                        new Order(secondOrderId,customer.getId(),UUID.randomUUID(),2,20)
                )
        );
        //when
        long totalCustomerAmount = orderService.getTotalCustomerAmount(customer);
        //then
        Assertions.assertEquals(30,orderService.getTotalCustomerAmount( customer));

    }

}