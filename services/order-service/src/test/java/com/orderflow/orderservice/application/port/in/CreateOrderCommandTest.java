package com.orderflow.orderservice.application.port.in;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class CreateOrderCommandTest {

    @Test
    void shouldProtectItemsListFromExternalChanges() {
        List<CreateOrderItemCommand> items = new ArrayList<>();

        CreateOrderCommand command = new CreateOrderCommand(
                UUID.randomUUID(),
                items
        );

        items.add(
                new CreateOrderItemCommand(
                        UUID.randomUUID(),
                        1,
                        new BigDecimal("10.00")
                )
        );

        assertEquals(0, command.items().size());
    }

    @Test
    void shouldRejectNullItemsList() {
        assertThrows(
                NullPointerException.class,
                () -> new CreateOrderCommand(
                        UUID.randomUUID(),
                        null
                )
        );
    }
}