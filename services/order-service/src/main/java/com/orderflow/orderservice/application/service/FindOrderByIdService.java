package com.orderflow.orderservice.application.service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.orderflow.orderservice.application.port.in.FindOrderByIdUseCase;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;

public class FindOrderByIdService implements FindOrderByIdUseCase {
    private final OrderRepository orderRepository;
    
    public FindOrderByIdService(OrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(orderRepository);
    }

    @Override 
    public Optional<Order> execute(UUID id){
        Objects.requireNonNull(id, "id cannot be null");
        return orderRepository.findById(id);
    }
}
