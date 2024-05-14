package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Product;
import ru.shop.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceTest {

    private final ProductRepository repository = Mockito.mock();
    private final ProductService productService = new ProductService(repository);

    @DisplayName("Сервис должен загружать продукт по id")
    @Test
    public void shouldGetCustomer() {
        // given
        UUID productId = UUID.randomUUID();
        var mockedProduct = new Product();
        Mockito
                .when(repository.findById(productId))
                .thenReturn(Optional.of(mockedProduct));

        // when
        Product product = productService.getById(productId);

        // then
        assertThat(product).isEqualTo(mockedProduct);
    }

    @Test
    public void shouldThrowWhenProductNotFound() {
        // then
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> productService.getById(UUID.randomUUID())
        );

        assertThatThrownBy(
                () -> productService.getById(UUID.randomUUID())
        ).isInstanceOf(
                EntityNotFoundException.class
        );
    }
}