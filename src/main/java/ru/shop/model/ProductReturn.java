package ru.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.UUID;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_return")
public class ProductReturn {
    @Id
    private UUID id;
    @NotNull
    private UUID orderId;
    @NotNull
    private LocalDate date;
    @NotNull
    private Long quantity;
}
