package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public void save(Product product) {
        repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public List<Product> findByProductType(ProductType productType) {
        List<Product> result = new ArrayList<>();
        for (Product product : repository.findAll()) {
            if (product.getProductType() == productType) {
                result.add(product);
            }
        }
        return result;
    }

    public Product getById(UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
