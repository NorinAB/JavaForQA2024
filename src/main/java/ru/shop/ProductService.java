package ru.shop;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public void save(Product product) {
        repository.save(product);
    }

    public List<Product> findByProductType(ProductType productType) {

        List<Product> newList = new ArrayList<>();

        for (var filter : repository.findAll()) {
            if (filter.productType().equals(productType)) {
                newList.add(filter);
            }
        }

        return newList;

        // return repository.findAll().stream().filter(sth -> sth.productType().equals(productType)).toList();



    }

}
