package com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.mapper;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderItem;
import com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.entity.OrderEntity;
import com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.entity.OrderItemEntity;

@Component 
public class OrderPersistenceMapper {

    public OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity(
                order.getId(),
                order.getCustomerId(),
                order.getStatus()
        );

        for (OrderItem item : order.getItems()) {
            entity.addItem(
                    new OrderItemEntity(
                            UUID.randomUUID(),
                            item.getProductId(),
                            item.getQuantity(),
                            item.getUnitPrice()
                    )
            );
        }

        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        List<OrderItem> items = entity.getItems()
                .stream()
                .map(item -> OrderItem.of(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getUnitPrice()
                ))
                .toList();

        return Order.reconstitute(
                entity.getId(),
                entity.getCustomerId(),
                entity.getStatus(),
                items
        );
    }
}