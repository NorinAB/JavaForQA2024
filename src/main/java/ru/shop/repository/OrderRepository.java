package ru.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.model.Order;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
