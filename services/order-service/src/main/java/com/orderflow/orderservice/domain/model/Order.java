package com.orderflow.orderservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Order {
    private final UUID id;
    private final UUID customerId;
    private final OrderStatus status;
    
    private Order(UUID id, UUID customerId, OrderStatus status){
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "customerId cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    public static Order create(UUID customerId) {
        return new Order(
            UUID.randomUUID(), 
            customerId, 
            OrderStatus.CREATED);
    }
    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    
}
