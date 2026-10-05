package com.orderflow.orderservice.application.port.in;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record CreateOrderCommand(
    UUID customerId,
    List<CreateOrderItemCommand> items
) {
   public CreateOrderCommand {
    customerId = Objects.requireNonNull(customerId, "customerId cannot be null");

    items = List.copyOf(Objects.requireNonNull(items, "items cannot be null"));
   } 
}
