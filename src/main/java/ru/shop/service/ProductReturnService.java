package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.exception.BadProductReturnCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.ProductReturnRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductReturnService {

    private final ProductReturnRepository repository;

    public void add(Order order, long count) {
        if (count > order.getCount()) {

            throw new BadProductReturnCountException();

        }
    }

    public List<ProductReturn> findAll(){
        return repository.findAll();
    }

    public ProductReturn findById(UUID id){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
