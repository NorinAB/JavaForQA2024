package ru.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.service.CustomerService;
import ru.shop.service.OrderService;
import ru.shop.service.ProductService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable UUID id) {
        return orderService.getById(id);
    }

    @PostMapping
    public void save(UUID productId, UUID customerId, int count) {
        Product product = productService.getById(productId);
        Customer customer = customerService.getById(customerId);
        orderService.add(customer, product, count);
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> getByCustomerId(UUID customerId) {
        Customer customer = customerService.getById(customerId);
        return orderService.findByCustomer(customer);
    }

    @GetMapping("/customer/{customerId}/total")
    public long getCustomerTotal(UUID customerId) {
        Customer customer = customerService.getById(customerId);
        return orderService.getTotalCustomerAmount(customer);
    }

}