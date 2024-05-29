package ru.shop.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.model.ProductReturn;
import ru.shop.service.ProductReturnService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productReturn")
public class ProductReturnController {
    private ProductReturnService productReturnService;

    @GetMapping
    public List<ProductReturn> getAll() {
        return productReturnService.findAll();
    }

    @GetMapping("/{id}")
    public ProductReturn getById(@PathVariable UUID id) {
        return productReturnService.findById(id);
    }

}
