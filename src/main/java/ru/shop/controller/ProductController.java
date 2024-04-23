package ru.shop.controller;

import org.springframework.web.bind.annotation.*;
import ru.shop.model.Product;
import ru.shop.repository.ProductRepository;
import ru.shop.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductService productService = new ProductService(
            new ProductRepository()
    );

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    @PostMapping
    public void save(@RequestBody Product product) {
        productService.save(product);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id) {
        return productService.getById(id);
    }

}