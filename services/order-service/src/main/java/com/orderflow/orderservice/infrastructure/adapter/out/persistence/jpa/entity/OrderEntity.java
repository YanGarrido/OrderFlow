package com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.orderflow.orderservice.domain.model.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table (name = "orders")
public class OrderEntity {

    @Id 
    @Column (nullable = false)
    private UUID id;

    @Column (name = "customer_id", nullable = false)
    private UUID customerId;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 30)
    private OrderStatus status;

    @OneToMany (
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<OrderItemEntity> items = new ArrayList<>();

    protected OrderEntity() {
    }
    public OrderEntity(
        UUID id,
        UUID customerId,
        OrderStatus status
) {
    this.id = id;
    this.customerId = customerId;
    this.status = status;
}

public void addItem(OrderItemEntity item) {
    items.add(item);
    item.setOrder(this);
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

public List<OrderItemEntity> getItems() {
    return List.copyOf(items);
}

}
