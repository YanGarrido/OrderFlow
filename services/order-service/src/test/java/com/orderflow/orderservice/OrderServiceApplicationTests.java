package com.orderflow.orderservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderStatus;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Test
    void contextLoads() {
        assertNotNull(createOrderUseCase);
    }

    @Test
    void shouldCreateOrderThroughSpringContext() {
        Order order = createOrderUseCase.execute(
                new CreateOrderCommand(UUID.randomUUID())
        );

        assertEquals(OrderStatus.CREATED, order.getStatus());
    }
}