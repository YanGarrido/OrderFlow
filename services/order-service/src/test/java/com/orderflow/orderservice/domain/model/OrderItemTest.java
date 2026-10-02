package com.orderflow.orderservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void shouldCalculateItemTotal() {
        OrderItem item = OrderItem.of(
                UUID.randomUUID(),
                3,
                new BigDecimal("19.90")
        );

        assertEquals(
                new BigDecimal("59.70"),
                item.total()
        );
    }

    @Test
    void shouldRejectNonPositiveQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderItem.of(
                        UUID.randomUUID(),
                        0,
                        new BigDecimal("19.90")
                )
        );
    }

    @Test
    void shouldRejectNonPositiveUnitPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderItem.of(
                        UUID.randomUUID(),
                        1,
                        BigDecimal.ZERO
                )
        );
    }
}