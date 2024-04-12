package ru.shop;

import java.util.List;
import java.util.UUID;

public class OrderService {

    //TODO:использует соответствующий репозиторий (передаётся в конструкторе при создании)
    //* добавить метод (add) добавления заказа. На вход методу подаём Customer, Product, count
    //* Создать ошибку BadOrderCountException (пакет ru.shop.exception)
    //* Если количество товара при создании меньше или равно нулю, то нужно генерировать ошибку (BadOrderCountException)
    //* добавить метод (findAll) получения всех элементов
    //* добавить метод (findByCustomer) получения с фильтрацией по пользователю
    //* добавить метод (getTotalCustomerAmount) подсчёта суммы для оплаты для пользователя


    private final OrderRepository repository;


    public OrderService(OrderRepository repository) {

        this.repository = repository;

    }

    public void add(Customer customer, Product product, int count) throws BadOrderCountException {

        repository.save(new Order(UUID.randomUUID().toString(), customer.id(), product.id(), count, product.cost() * count));

        if (count <= 0) {
            throw new BadOrderCountException();
        }
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public List<Order> findByCustomer(Customer customer) {
        return repository.findAll().stream().filter(sth -> sth.customerId().equals(customer.id())).toList();
    }

    public long getTotalCustomerAmount(Customer customer) {
        long total = 0;

        for (var ord : repository.findAll().stream().filter(sth -> sth.customerId().equals(customer.id())).toList()) {
            total += ord.amount();
        }

        return total;
    }


}
