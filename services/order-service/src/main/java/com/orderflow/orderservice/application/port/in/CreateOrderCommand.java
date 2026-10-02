package com.orderflow.orderservice.application.port.in;
import java.util.UUID;

public record CreateOrderCommand(UUID customerId) {
    
}
