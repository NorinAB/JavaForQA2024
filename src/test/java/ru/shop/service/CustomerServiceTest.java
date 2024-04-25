package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerServiceTest {
    private final CustomerRepository repository = Mockito.mock();
    private final CustomerService customerService = new CustomerService(repository);

    @Test
    public void shouldGetCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer mockedCustomer = new Customer(customerId, "name", "phone", 10);
        when(repository.findById(customerId)).thenReturn(Optional.of(mockedCustomer));
        Customer customer = customerService.getById(customerId);
        assertThat(customer).isEqualTo(mockedCustomer);
    }
    @Test
    public void shouldThrowWhenCustomerNotFound() {
        // then
        assertThatThrownBy(() -> customerService.getById(UUID.randomUUID())).isInstanceOf(EntityNotFoundException.class);
    }
    @Test
    public void shouldSaveCustomer() {
        Customer customer = new Customer();
        customerService.save(customer);
        Mockito.verify(repository, Mockito.times(1)).save(customer);
    }
    @Test
    public void shouldCallFindAllCustomerWhenFindAllCustomers() {
            customerService.findAll();
            Mockito.verify(repository).findAll();
    }


}
