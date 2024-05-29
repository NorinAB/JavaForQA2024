package ru.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
// Сущности храниться в БД
@Entity
// Задаём таблицы для хранения
@Table(name = "product")
public class Product {

    // первичный ключ
    @Id
    private UUID id;

    private String name;

    long cost;

    // ENUM храниться в БД как строка
    @Enumerated(EnumType.STRING)
    private ProductType productType;
}