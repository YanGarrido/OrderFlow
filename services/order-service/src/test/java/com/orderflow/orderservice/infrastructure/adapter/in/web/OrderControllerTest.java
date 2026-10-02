package com.orderflow.orderservice.infrastructure.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.domain.model.Order;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
        Order order = Order.create(customerId);

        when(createOrderUseCase.execute(any()))
                .thenReturn(order);

        CreateOrderRequest request =
                new CreateOrderRequest(customerId);

        mockMvc.perform(
                post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(order.getId().toString()))
        .andExpect(jsonPath("$.customerId").value(customerId.toString()))
        .andExpect(jsonPath("$.status").value("CREATED"));

        verify(createOrderUseCase).execute(any());
    }
}