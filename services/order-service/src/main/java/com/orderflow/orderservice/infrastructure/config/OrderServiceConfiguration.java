package com.orderflow.orderservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.application.service.CreateOrderService;
import com.orderflow.orderservice.infrastructure.adapter.out.persistence.memory.InMemoryOrderRepository;

@Configuration
public class OrderServiceConfiguration {

    @Bean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean 
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository) {
        return new CreateOrderService(orderRepository);
    }

    

}
