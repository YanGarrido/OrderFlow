package com.orderflow.orderservice;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.orderflow.orderservice.application.port.in.CancelOrderUseCase;
import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.application.port.in.FindOrderByIdUseCase;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.orderflow.orderservice.application.port.in.CreateOrderItemCommand;

@Testcontainers
@SpringBootTest
class OrderServiceApplicationTests {
    @Container 
    @ServiceConnection 
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");
    
    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired 
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Autowired
    private CancelOrderUseCase cancelOrderUseCase;

    @Test
    void contextLoads() {
        assertNotNull(createOrderUseCase);
    }

    @Test
    void shouldCreateOrderThroughSpringContext() {
        UUID customerId = UUID.randomUUID();

        CreateOrderItemCommand item = new CreateOrderItemCommand(
                UUID.randomUUID(),
                1,
                new BigDecimal("25.00")
        );

        Order order = createOrderUseCase.execute(
                new CreateOrderCommand(
                        customerId,
                        List.of(item)
                )
        );

        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(new BigDecimal("25.00"), order.total());
    }

    @Test
    void shouldPersistAndFindOrder() {
        UUID customerId = UUID.randomUUID();

        CreateOrderItemCommand item = new CreateOrderItemCommand(
            UUID.randomUUID(),
            2,
            new BigDecimal("19.90")
        );

        Order createdOrder = createOrderUseCase.execute(new CreateOrderCommand(
            customerId,
            List.of(item)
        ));

        Optional<Order> foundOrder = findOrderByIdUseCase.execute(createdOrder.getId());

        assertTrue(foundOrder.isPresent());
        assertEquals(createdOrder.getId(), foundOrder.get().getId());
        assertEquals(customerId, foundOrder.get().getCustomerId());
        assertEquals(new BigDecimal("39.80"), foundOrder.get().total());
        assertEquals(1, foundOrder.get().getItems().size());

    }
    @Test
void shouldPersistCancelledOrder() {
    UUID customerId = UUID.randomUUID();

    CreateOrderItemCommand item = new CreateOrderItemCommand(
            UUID.randomUUID(),
            1,
            new BigDecimal("25.00")
    );

    Order createdOrder = createOrderUseCase.execute(
            new CreateOrderCommand(
                    customerId,
                    List.of(item)
            )
    );

    assertEquals(
            OrderStatus.CREATED,
            createdOrder.getStatus()
    );

    Order cancelledOrder = cancelOrderUseCase.execute(
            createdOrder.getId()
    );

    assertEquals(
            OrderStatus.CANCELLED,
            cancelledOrder.getStatus()
    );

    Optional<Order> foundOrder =
            findOrderByIdUseCase.execute(createdOrder.getId());

    assertTrue(foundOrder.isPresent());
    assertEquals(
            OrderStatus.CANCELLED,
            foundOrder.get().getStatus()
    );
    assertEquals(
            new BigDecimal("25.00"),
            foundOrder.get().total()
    );
}

}