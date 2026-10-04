package com.orderflow.orderservice.application.port.in;

import java.util.Optional;
import java.util.UUID;

import com.orderflow.orderservice.domain.model.Order;

public interface FindOrderByIdUseCase {
    Optional<Order> execute(UUID id);
}
