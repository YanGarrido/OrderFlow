package com.orderflow.orderservice.infrastructure.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.domain.model.Order;
import com.orderflow.orderservice.domain.model.OrderItem;

class OrderControllerTest {

    private final CreateOrderUseCase createOrderUseCase =
            mock(CreateOrderUseCase.class);

    private final MockMvc mockMvc = standaloneSetup(
            new OrderController(createOrderUseCase)
    ).build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateOrder() throws Exception {
        UUID customerId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Order order = Order.create(customerId);

        order.addItem(
                OrderItem.of(
                        productId,
                        2,
                        new BigDecimal("19.90")
                )
        );

        when(createOrderUseCase.execute(any(CreateOrderCommand.class)))
                .thenReturn(order);

        CreateOrderItemRequest itemRequest =
                new CreateOrderItemRequest(
                        productId,
                        2,
                        new BigDecimal("19.90")
                );

        CreateOrderRequest request = new CreateOrderRequest(
                customerId,
                List.of(itemRequest)
        );

        mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(order.getId().toString()))
        .andExpect(jsonPath("$.customerId").value(customerId.toString()))
        .andExpect(jsonPath("$.status").value("CREATED"))
        .andExpect(jsonPath("$.total").value(39.8));

        verify(createOrderUseCase)
                .execute(any(CreateOrderCommand.class));
    }
}