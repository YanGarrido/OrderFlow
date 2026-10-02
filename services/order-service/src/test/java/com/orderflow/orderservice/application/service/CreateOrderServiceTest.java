package com.orderflow.orderservice.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderStatus;

class CreateOrderServiceTest {

    @Test
    void shouldCreateAndSaveOrder() {
        OrderRepository orderRepository = mock(OrderRepository.class);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderService service =
                new CreateOrderService(orderRepository);

        UUID customerId = UUID.randomUUID();

        Order result = service.execute(
                new CreateOrderCommand(customerId)
        );

        assertEquals(customerId, result.getCustomerId());
        assertEquals(OrderStatus.CREATED, result.getStatus());

        verify(orderRepository).save(any(Order.class));
    }
}