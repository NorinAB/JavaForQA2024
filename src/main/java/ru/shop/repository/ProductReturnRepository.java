package ru.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.model.Product;
import ru.shop.model.ProductReturn;

import java.util.UUID;
@Repository
public interface ProductReturnRepository extends JpaRepository<ProductReturn, UUID> {

}
