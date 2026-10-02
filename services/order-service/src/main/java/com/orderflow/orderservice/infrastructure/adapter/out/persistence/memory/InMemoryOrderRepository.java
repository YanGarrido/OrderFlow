package com.orderflow.orderservice.infrastructure.adapter.out.persistence.memory;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        Objects.requireNonNull(order, "order cannot be null");

        orders.put(order.getId(), order);

        return order;
    }
    
    
}
