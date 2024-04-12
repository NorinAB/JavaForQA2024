package ru.shop;

import java.util.List;

public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public void save (Customer customer) {
        repository.save(customer);
    }

}
