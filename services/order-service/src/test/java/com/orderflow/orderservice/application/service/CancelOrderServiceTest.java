package com.orderflow.orderservice.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.orderflow.orderservice.application.exception.OrderNotFoundException;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderStatus;

class CancelOrderServiceTest {

    @Test
    void shouldCancelCreatedOrder() {
        OrderRepository repository = mock(OrderRepository.class);
        Order order = Order.create(UUID.randomUUID());

        when(repository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        when(repository.save(order))
                .thenReturn(order);

        CancelOrderService service =
                new CancelOrderService(repository);

        Order result = service.execute(order.getId());

        assertEquals(OrderStatus.CANCELLED, result.getStatus());
        verify(repository).save(order);
    }

    @Test
    void shouldThrowWhenOrderDoesNotExist() {
        OrderRepository repository = mock(OrderRepository.class);
        UUID orderId = UUID.randomUUID();

        when(repository.findById(orderId))
                .thenReturn(Optional.empty());

        CancelOrderService service =
                new CancelOrderService(repository);

        assertThrows(
                OrderNotFoundException.class,
                () -> service.execute(orderId)
        );

        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void shouldNotCancelAlreadyCancelledOrder() {
        OrderRepository repository = mock(OrderRepository.class);
        Order order = Order.create(UUID.randomUUID());

        order.cancel();

        when(repository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        CancelOrderService service =
                new CancelOrderService(repository);

        assertThrows(
                IllegalStateException.class,
                () -> service.execute(order.getId())
        );

        verify(repository, never()).save(any(Order.class));
    }
}