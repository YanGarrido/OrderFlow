package com.orderflow.orderservice.application.port.in;

import com.orderflow.orderservice.domain.model.Order;

public interface CreateOrderUseCase {

    Order execute(CreateOrderCommand command);
    
}
