package com.orderflow.orderservice.application.port.in;

public interface FindOrdersUseCase {
    OrderPage execute(FindOrdersQuery query);
}
