package com.orderflow.orderservice.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.orderflow.orderservice.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
}
