package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.shop.exception.BadProductReturnCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.*;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductReturnRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductReturnServiceTest {
    private final ProductReturnRepository repository = Mockito.mock();
    private final OrderRepository orderRepository = Mockito.mock();
    private final ProductReturnService productReturnService = new ProductReturnService(repository);
    @Test
    public void shouldAddProductReturn() {
        var order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 3, 6);
        var productReturn = new ProductReturn(UUID.randomUUID(), order.getId(), LocalDate.now(), order.getCount() - 1);

        productReturnService.add(order, productReturn.getQuantity());
        ArgumentCaptor<ProductReturn> orderArgumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(repository, Mockito.times(1)).save(orderArgumentCaptor.capture());
        ProductReturn savedReturn = orderArgumentCaptor.getValue();
        Assertions.assertEquals(productReturn.getQuantity(), savedReturn.getQuantity());
        Assertions.assertEquals(order.getId(), savedReturn.getOrderId());
    }
    @Test
    public void shouldThrowWhenOrderCountLessThanGivenCount() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 2, 3);
        Mockito.when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Assertions.assertThrows(
                BadProductReturnCountException.class,
                () -> productReturnService.add(order, 5L)
        );
    }
    @Test
    public void shouldGetProductReturn() {
        UUID productReturnId = UUID.randomUUID();
        ProductReturn mockedProductReturn = new ProductReturn(productReturnId, UUID.randomUUID(), LocalDate.now(), 10L);
        when(repository.findById(productReturnId)).thenReturn(Optional.of(mockedProductReturn));
        ProductReturn productReturn = productReturnService.findById(productReturnId);
        Assertions.assertEquals(mockedProductReturn, productReturn);
    }
    @Test
    public void shouldThrowWhenProductNotFound() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> productReturnService.findById(UUID.randomUUID())
        );
    }
    @Test
    public void shouldGetAllProductReturn() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 2, 10);
        Order order1 = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 4, 20);
        Order order2 = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 6, 30);
        ProductReturn productReturn1 = new ProductReturn(UUID.randomUUID(), order.getId(), LocalDate.now(), order.getCount() - 1);
        ProductReturn productReturn2 = new ProductReturn(UUID.randomUUID(), order1.getId(), LocalDate.now(), order1.getCount() - 1);
        ProductReturn productReturn3 = new ProductReturn(UUID.randomUUID(), order2.getId(), LocalDate.now(), order2.getCount() - 1);
        Mockito.when(repository.findAll()).thenReturn(List.of(
                productReturn1, productReturn2, productReturn3
        ));
        List<ProductReturn> productReturns = productReturnService.findAll();
        Assertions.assertEquals(3, productReturns.size());
        assertThat(productReturns).hasSize(3).extracting(ProductReturn::getId).containsExactly(productReturn1.getId(),
                productReturn2.getId(), productReturn3.getId());
    }
}
