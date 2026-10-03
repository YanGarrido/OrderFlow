package com.orderflow.orderservice.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Order {
    private final UUID id;
    private final UUID customerId;
    private OrderStatus status;
    private List<OrderItem> items;
    
    private Order(UUID id, UUID customerId, OrderStatus status, List<OrderItem> items) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "customerId cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
        this.items = new ArrayList<>(Objects.requireNonNull(items, "items cannot be null"));
    }

    public static Order create(UUID customerId) {
        return new Order(
            UUID.randomUUID(), 
            customerId, 
            OrderStatus.CREATED,
            List.of()
        );
    }

    public static Order reconstitute(
        UUID id,
        UUID customerId,
        OrderStatus status,
        List<OrderItem> items
    ) {
        return new Order(id, customerId, status, items);
    }

    public void cancel() {
        if(status != OrderStatus.CREATED){
            throw new IllegalStateException("Order cannot be cancelled from status" + status);
        }
        this.status = OrderStatus.CANCELLED;
    }
    public void addItem(OrderItem item){
        Objects.requireNonNull(item, "item cannot be null");

        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot add item to order with status " + status);
        }
        items.add(item);
    }

    public BigDecimal total() {
        return items.stream()
                .map(OrderItem::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
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
