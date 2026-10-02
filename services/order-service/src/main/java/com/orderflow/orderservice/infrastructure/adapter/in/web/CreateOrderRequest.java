package com.orderflow.orderservice.infrastructure.adapter.in.web;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
    @NotNull(message = "Customer ID is required")
    UUID customerId
) {
    
}
