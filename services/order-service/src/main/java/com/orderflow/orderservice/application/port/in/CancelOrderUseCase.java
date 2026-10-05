package com.orderflow.orderservice.application.port.in;

import java.util.UUID;

import com.orderflow.orderservice.domain.model.Order;

public interface CancelOrderUseCase {
    
    Order execute(UUID orderId);
}
