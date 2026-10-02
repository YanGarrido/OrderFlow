package com.orderflow.orderservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
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

    @Test 
    void shouldCancelCreatedOrder() {
        Order order = Order.create(UUID.randomUUID());
        
        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldNotCancelAlreadyCancelledOrder() {
        Order order = Order.create(UUID.randomUUID());
        order.cancel();

        assertThrows(
            IllegalStateException.class,
            order::cancel
        );
    }

    @Test
void shouldAddItemAndCalculateOrderTotal() {
    Order order = Order.create(UUID.randomUUID());

    OrderItem item = OrderItem.of(
            UUID.randomUUID(),
            2,
            new BigDecimal("19.90")
    );

    order.addItem(item);

    assertEquals(new BigDecimal("39.80"), order.total());
    assertEquals(1, order.getItems().size());
}

@Test
void shouldNotAddItemToCancelledOrder() {
    Order order = Order.create(UUID.randomUUID());

    order.cancel();

    OrderItem item = OrderItem.of(
            UUID.randomUUID(),
            1,
            new BigDecimal("10.00")
    );

    assertThrows(
            IllegalStateException.class,
            () -> order.addItem(item)
    );
}

@Test
void shouldNotExposeMutableItemCollection() {
    Order order = Order.create(UUID.randomUUID());

    order.addItem(
            OrderItem.of(
                    UUID.randomUUID(),
                    1,
                    new BigDecimal("10.00")
            )
    );

    assertThrows(
            UnsupportedOperationException.class,
            () -> order.getItems().clear()
    );
}
}
