package com.orderflow.orderservice.application.service;

import java.util.Objects;
import java.util.UUID;

import com.orderflow.orderservice.application.exception.OrderNotFoundException;
import com.orderflow.orderservice.application.port.in.CancelOrderUseCase;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;

public class CancelOrderService implements CancelOrderUseCase {
    private final OrderRepository orderRepository;

    public CancelOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override 
    public Order execute(UUID orderId) {
        Objects.requireNonNull(orderId, "orderId cannot be null");

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.cancel();

        return orderRepository.save(order);
    }
    
}
