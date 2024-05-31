package ru.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    UUID id;
    UUID customerId;
    UUID productId;
    long count;
    long amount;
}
