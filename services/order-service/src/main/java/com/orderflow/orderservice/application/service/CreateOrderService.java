package com.orderflow.orderservice.application.service;

import java.util.Objects;
import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderItem;

public class CreateOrderService implements CreateOrderUseCase {
    private final OrderRepository orderRepository;

    public CreateOrderService(OrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(orderRepository);
    }

    @Override 
    public Order execute(CreateOrderCommand command) {
        Objects.requireNonNull(command);

        Order order = Order.create(command.customerId());

        command.items().forEach(item -> order.addItem(
            OrderItem.of(
                item.productId(),
                item.quantity(), 
                item.unitPrice()
            )
        ));

        return orderRepository.save(order);
    }
    
}
