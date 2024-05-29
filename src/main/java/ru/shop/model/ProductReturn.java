package ru.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProductReturn")
public class ProductReturn {
    @Id
    private UUID id;
    private UUID orderId;
    private LocalDate date;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductType productType;
}
