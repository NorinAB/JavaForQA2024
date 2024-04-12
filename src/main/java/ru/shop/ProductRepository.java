package ru.shop;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    List<Product> productList = new ArrayList<>();

    public void save(Product product) {
        productList.add(product);
    }

    public List<Product> findAll() {
        return new ArrayList<>(productList);
    }

}
