package ru.shop;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    List<Customer> customerList = new ArrayList<>();

    public void save(Customer customer) {
        customerList.add(customer);
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customerList);
    }

}
