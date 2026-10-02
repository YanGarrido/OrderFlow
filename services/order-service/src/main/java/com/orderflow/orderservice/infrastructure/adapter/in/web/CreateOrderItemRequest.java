package com.orderflow.orderservice.infrastructure.adapter.in.web;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderItemRequest(
    @NotNull (message = "Product ID is required")
    UUID productId,

    @NotNull (message="Quantity is required")
    @Positive (message = "Quantity must be greater than zero")
    Integer quantity,

    @NotNull (message = "Unit price is required")
    @DecimalMin (
        value = "0.01",
        message = "Unit price must be greater than zero"
    )
    BigDecimal unitPrice
) {
    
}
