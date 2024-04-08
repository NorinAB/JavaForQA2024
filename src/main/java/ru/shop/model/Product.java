package ru.shop.model;

import java.util.UUID;

public class Product {
    private final UUID id;
    private final String name;
    private final long cost;
    private final ProductType productType;

    public Product(UUID id, String name, long cost, ProductType productType) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", productType=" + productType +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCost() {
        return cost;
    }

    public ProductType getProductType() {
        return productType;
    }
}
