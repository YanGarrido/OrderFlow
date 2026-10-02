package com.orderflow.orderservice.infrastructure.adapter.out.persistence.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.orderflow.orderservice.domain.model.Order;

class InMemoryOrderRepositoryTest {

    @Test
    void shouldSaveOrder() {
        InMemoryOrderRepository repository =
                new InMemoryOrderRepository();

        Order order = Order.create(UUID.randomUUID());

        Order savedOrder = repository.save(order);

        assertSame(order, savedOrder);
        assertEquals(order.getId(), savedOrder.getId());
    }
}