package com.orderflow.orderservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test 
    void shouldCreateOrderWithCreatedStatus() {
        UUID customerId = UUID.randomUUID();
        
        Order order = Order.create(customerId);

        assertNotNull(order.getId());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test 
    void shouldNotCreateOrderWithoutCustomer() {
        assertThrows(
            NullPointerException.class,
            () -> Order.create(null));
    }
}
