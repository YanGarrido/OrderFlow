package com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.mapper.OrderPersistenceMapper;
import com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.repository.SpringDataOrderRepository;

@Repository
public class JpaOrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository repository;
    private final OrderPersistenceMapper mapper;

    public JpaOrderRepositoryAdapter(
            SpringDataOrderRepository repository,
            OrderPersistenceMapper mapper
    ) {
        this.repository = Objects.requireNonNull(repository);
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Override
    public Order save(Order order) {
        repository.save(mapper.toEntity(order));

        return order;
    }
    @Override 
    public Optional<Order> findById(UUID id){
        return repository.findById(id)
                .map(mapper::toDomain);
    }
}