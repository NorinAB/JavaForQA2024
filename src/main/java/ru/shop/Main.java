package ru.shop;

import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductRepository;
import ru.shop.service.ProductService;

import java.util.UUID;

public class Main {


    public static void main(String[] args) {
        ProductService productService = new ProductService(
                new ProductRepository()
        );

        CustomerRepository customerRepository = new CustomerRepository();
        OrderRepository orderRepository = new OrderRepository();

        System.out.println(ProductType.SERVICE.name());

        Product ladaKalina = new Product(UUID.randomUUID(), "Lada Kalina", 100, ProductType.GOOD);
        productService.save(ladaKalina);
        Product fordMustang = new Product(UUID.randomUUID(), "Ford Mustang", 10000, ProductType.GOOD);
        productService.save(fordMustang);
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

        Customer ivan = new Customer(UUID.randomUUID(), "Ivanushka", "123456", 16);
        customerRepository.save(ivan);
        Customer king = new Customer(UUID.randomUUID(), "King", "9999999", 42);
        customerRepository.save(king);

        System.out.println("-- ALL CUSTOMERS --");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }

        Order ivanushkaLadaOrder = new Order(
                UUID.randomUUID(),
                ivan.getId(),
                ladaKalina.getId(),
                2,
                200
        );
        orderRepository.save(ivanushkaLadaOrder);

        for (Order order : orderRepository.findAll()) {
            System.out.println(order);
        }
    }

}