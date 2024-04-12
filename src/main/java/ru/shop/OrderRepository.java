package ru.shop;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    List<Order> orderList = new ArrayList<>();

    public void save(Order order) {
        orderList.add(order);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderList);
    }

}
