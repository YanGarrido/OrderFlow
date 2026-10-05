package com.orderflow.orderservice.application.service;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.orderflow.orderservice.application.port.in.FindOrdersQuery;
import com.orderflow.orderservice.application.port.in.FindOrdersUseCase;
import com.orderflow.orderservice.application.port.in.OrderPage;
import com.orderflow.orderservice.application.port.out.OrderRepository;

public class FindOrdersService implements FindOrdersUseCase {

    private final OrderRepository orderRepository;

    public FindOrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional (readOnly = true)
    @Override 
    public OrderPage execute(FindOrdersQuery query) {
        Objects.requireNonNull(query);
        return orderRepository.findAll(query);
    }
    
}
