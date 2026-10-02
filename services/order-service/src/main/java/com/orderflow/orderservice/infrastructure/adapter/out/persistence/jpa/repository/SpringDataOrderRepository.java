package com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.orderservice.infrastructure.adapter.out.persistence.jpa.entity.OrderEntity;

public interface SpringDataOrderRepository
        extends JpaRepository<OrderEntity, UUID> {
}