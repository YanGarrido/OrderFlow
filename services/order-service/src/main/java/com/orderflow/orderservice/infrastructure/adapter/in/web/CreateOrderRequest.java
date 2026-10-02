package com.orderflow.orderservice.infrastructure.adapter.in.web;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
    @NotNull(message = "Customer ID is required")
    UUID customerId,

    @NotEmpty (message = "Order must contain at least one item")
    List<@Valid CreateOrderItemRequest> items


) {
    
}
