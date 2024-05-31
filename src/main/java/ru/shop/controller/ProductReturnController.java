package ru.shop.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductReturn;
import ru.shop.service.OrderService;
import ru.shop.service.ProductReturnService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product_return")
public class ProductReturnController {
    private final ProductReturnService productReturnService;
    private final OrderService orderService;


    @GetMapping
    public List<ProductReturn> getAll() {
        return productReturnService.findAll();
    }
    @GetMapping("/{id}") public ProductReturn getById(@PathVariable UUID id){
        return productReturnService.findById(id);
    }
    @PostMapping
    public void save(@RequestBody ProductReturnRequest request) {
        Order order = orderService.getById(request.getOrderId());
        productReturnService.add(order, request.getCount());
    }
}
@Getter
class ProductReturnRequest {
    private UUID orderId;
    private Long count;
}
