package com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.mapper;

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
}