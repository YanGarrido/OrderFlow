package com.orderflow.orderservice.application.port.in;

import java.util.List;
import java.util.Objects;

import com.orderflow.orderservice.domain.model.Order;

public record OrderPage(
    List<Order> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) {
     public OrderPage {
        content = List.copyOf(Objects.requireNonNull(content));
     }
}
