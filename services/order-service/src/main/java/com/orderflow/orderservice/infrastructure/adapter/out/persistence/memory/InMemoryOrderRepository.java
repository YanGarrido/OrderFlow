package com.orderflow.orderservice.infrastructure.adapter.out.persistence.memory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.orderflow.orderservice.application.port.in.FindOrdersQuery;
import com.orderflow.orderservice.application.port.in.OrderPage;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> orders = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        Objects.requireNonNull(order, "order cannot be null");

        orders.put(order.getId(), order);

        return order;
    }

    @Override 
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override 
    public OrderPage findAll(FindOrdersQuery query) {
        List<Order> allOrders = orders.values()
                .stream()
                .sorted(Comparator.comparing(Order::getId))
                .toList();
        
        long offset = (long) query.page() * query.size();

        int fromIndex = offset >= allOrders.size() ? allOrders.size() : (int) offset;

        int toIndex = Math.min(fromIndex + query.size(), allOrders.size());

        List<Order> content = allOrders.subList(fromIndex, toIndex);

        int totalPages = (int) ((allOrders.size() + query.size() - 1L) / query.size());

        return new OrderPage(content, query.page(), query.size(), allOrders.size(), totalPages);
    }
    
    
}
