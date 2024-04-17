package ru.shop;

import ru.shop.exception.BadOrderCountException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductRepository;
import ru.shop.service.CustomerService;
import ru.shop.service.OrderService;
import ru.shop.service.ProductService;

import java.util.UUID;

public class Main {


    public static void main(String[] args) {
        ProductService productService = new ProductService(
                new ProductRepository()
        );

        CustomerService customerService = new CustomerService(
                new CustomerRepository()
        );

        OrderService orderService = new OrderService(
                new OrderRepository()
        );

        Product lada = new Product(UUID.randomUUID(), "Lada", 100, ProductType.GOOD);
        productService.save(lada);
        Product ford = new Product(UUID.randomUUID(), "Ford", 10000, ProductType.GOOD);
        productService.save(ford);
        Product carWashing = new Product(UUID.randomUUID(), "Car washing", 10, ProductType.SERVICE);
        productService.save(carWashing);

        System.out.println("-- ALL PRODUCTS --");
        for (Product product : productService.findAll()) {
            System.out.println(product);
        }

        System.out.println("-- GOODS --");
        for (Product product : productService.findByProductType(ProductType.GOOD)) {
            System.out.println(product);
        }

        System.out.println("-- SERVICES --");
        for (Product product : productService.findByProductType(ProductType.SERVICE)) {
            System.out.println(product);
        }

        Customer ivan = new Customer(UUID.randomUUID(), "Ivan", "1234567", 16);
        customerService.save(ivan);

        Customer petr = new Customer(UUID.randomUUID(), "Petr", "7777777", 25);
        customerService.save(petr);

        System.out.println("-- ALL CUSTOMERS --");
        for (Customer customer : customerService.findAll()) {
            System.out.println(customer);
        }

        orderService.add(ivan, lada, 2);
        orderService.add(ivan, ford, 2);

        try {
            orderService.add(petr, ford, 0);
        } catch (BadOrderCountException ex) {
            System.out.println("Adding order exception: BadOrderCountException");
        }

        orderService.add(petr, ford, 5);

        System.out.println("--- All orders");
        for (Order order : orderService.findAll()) {
            System.out.println(order);
        }

        System.out.println("--- Ivan's orders");
        for (Order order : orderService.findByCustomer(ivan)) {
            System.out.println(order);
        }
        System.out.println("Ivan's orders total amount = " + orderService.getTotalCustomerAmount(ivan));

        System.out.println("--- Petr's orders");
        for (Order order : orderService.findByCustomer(petr)) {
            System.out.println(order);
        }
        System.out.println("Petr's orders total amount = " + orderService.getTotalCustomerAmount(petr));

    }
}