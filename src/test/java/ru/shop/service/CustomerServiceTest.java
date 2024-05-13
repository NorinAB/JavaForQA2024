package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class CustomerServiceTest {

    private final CustomerRepository repository = Mockito.mock();
    private final CustomerService customerService = new CustomerService(repository);

    @Test
    public void shouldGetCustomer() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer mockedCustomer = new Customer();
        Mockito
                .when(repository.findById(customerId))
                .thenReturn(Optional.of(mockedCustomer));

        // when
        Customer customer = customerService.getById(customerId);

        // then
        Assertions.assertEquals(mockedCustomer, customer);
    }

    @Test
    public void shouldSaveCustomer() {
        // given
        Customer customer = new Customer();

        // when
        customerService.save(customer);

        // then
        Mockito.verify(repository).save(customer);
    }

    @Test
    public void shouldUseUserRepositoryToFindAllCustomers() {
        // given
        Customer oldCustomer = new Customer(UUID.randomUUID(), "name", "phone", 11);
        Customer youngCustomer = new Customer(UUID.randomUUID(), "name", "phone", 5);
        Mockito.when(repository.findAll()).thenReturn(
                List.of(
                        youngCustomer,
                        oldCustomer
                )
        );

        // when
        List<Customer> all = customerService.findAll();

        // then
        Mockito.verify(repository).findAll();
        Assertions.assertEquals(List.of(youngCustomer, oldCustomer), all);
    }

    @Test
    public void shouldThrowWhenCustomerNotFound() {
        // then
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> customerService.getById(UUID.randomUUID())
        );
    }
}