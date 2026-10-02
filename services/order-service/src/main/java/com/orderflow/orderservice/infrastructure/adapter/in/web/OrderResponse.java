package com.orderflow.orderservice.infrastructure.adapter.in.web;

import java.math.BigDecimal;
import java.util.UUID;

import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderStatus;

public record OrderResponse(
    UUID id, 
    UUID customerId, 
    OrderStatus status, 
    BigDecimal total
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getCustomerId(),
            order.getStatus(),
            order.total()
        );
    }
    
}
