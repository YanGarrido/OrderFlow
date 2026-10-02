package com.orderflow.orderservice.application.port.out;

import com.orderflow.orderservice.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
