package com.orderflow.orderservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orderflow.orderservice.application.port.in.CancelOrderUseCase;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.application.port.in.FindOrderByIdUseCase;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.application.service.CancelOrderService;
import com.orderflow.orderservice.application.service.CreateOrderService;
import com.orderflow.orderservice.application.service.FindOrderByIdService;

@Configuration
public class OrderServiceConfiguration {

    @Bean 
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository) {
        return new CreateOrderService(orderRepository);
    }

    @Bean 
    public FindOrderByIdUseCase findOrderByIdUseCase(OrderRepository orderRepository) {
        return new FindOrderByIdService(orderRepository);
    }

    @Bean 
    public CancelOrderUseCase cancelOrderUseCase(OrderRepository orderRepository) {
        return new CancelOrderService(orderRepository);
    }

    

}
