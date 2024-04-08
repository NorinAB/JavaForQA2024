package ru.shop.repository;

import ru.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        orders.add(order);
    }

    public List<Order> findAll() {
        return orders;
    }

}
