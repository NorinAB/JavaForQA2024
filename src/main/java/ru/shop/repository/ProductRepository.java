package ru.shop.repository;

import ru.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    List<Product> products = new ArrayList<>();

    public void save(Product product) {
        products.add(product);
    }

    public List<Product> findAll() {
        return products;
    }

}
