package com.orderflow.orderservice.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderItemCommand;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderStatus;

class CreateOrderServiceTest {

    @Test
    void shouldCreateAndSaveOrderWithItems() {
        OrderRepository orderRepository = mock(OrderRepository.class);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderService service =
                new CreateOrderService(orderRepository);

        UUID customerId = UUID.randomUUID();

        CreateOrderItemCommand item = new CreateOrderItemCommand(
                UUID.randomUUID(),
                2,
                new BigDecimal("19.90")
        );

        CreateOrderCommand command = new CreateOrderCommand(
                customerId,
                List.of(item)
        );

        Order result = service.execute(command);

        assertEquals(customerId, result.getCustomerId());
        assertEquals(OrderStatus.CREATED, result.getStatus());
        assertEquals(new BigDecimal("39.80"), result.total());
        assertEquals(1, result.getItems().size());

        verify(orderRepository).save(any(Order.class));
    }
}