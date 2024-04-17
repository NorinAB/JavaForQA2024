package ru.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    UUID id;
    UUID customerId;
    UUID productId;
    long count;
    long amount;
}
