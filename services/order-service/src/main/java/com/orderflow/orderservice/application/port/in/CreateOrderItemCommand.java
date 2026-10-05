package com.orderflow.orderservice.application.port.in;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record CreateOrderItemCommand(
    UUID productId,
    int quantity,
    BigDecimal unitPrice
) {
    public CreateOrderItemCommand {
        Objects.requireNonNull(productId, "productId cannot be null");

        Objects.requireNonNull(unitPrice, "unitPrice cannot be null");
    }
}
