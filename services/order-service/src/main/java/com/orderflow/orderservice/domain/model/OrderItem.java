package com.orderflow.orderservice.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class OrderItem {
    private final UUID productId;
    private final int quantity;
    private final BigDecimal unitPrice;

    private OrderItem(UUID productId, int quantity, BigDecimal unitPrice) {
        this.productId = Objects.requireNonNull(productId, "productId cannot be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice cannot be null");

        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
        this.quantity = quantity;
    }

    public static OrderItem of(
        UUID productId,
        int quantity,
        BigDecimal unitPrice
    ) {
        return new OrderItem(productId, quantity, unitPrice);
    }

    public BigDecimal total() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
