package ru.shop.model;

import java.util.UUID;

public class Order {
    private final UUID id;
    private final UUID customerId;
    private final UUID productId;
    private final long count;
    private final long amount;

    public Order(UUID id, UUID customerId, UUID productId, long count, long amount) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getCount() {
        return count;
    }

    public long getAmount() {
        return amount;
    }
}
