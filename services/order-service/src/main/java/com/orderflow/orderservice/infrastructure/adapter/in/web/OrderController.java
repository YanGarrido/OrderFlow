package com.orderflow.orderservice.infrastructure.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderItemCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.domain.model.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag (
    name = "Orders",
    description = "Operações relacionadas a pedidos"
)
@RestController 
@RequestMapping ("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }
    @Operation (
        summary = "Cria um pedido",
        description = "Cria um novo pedido para um cliente"
    )
   @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Pedido criado com sucesso"
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Dados de entrada inválidos"
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Operação incompatível com o estado atual"
        )
    })
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        Order order = createOrderUseCase.execute(
            new CreateOrderCommand(
                request.customerId(),
                request.items()
                    .stream()
                    .map(item -> new CreateOrderItemCommand(
                        item.productId(), 
                        item.quantity(), 
                        item.unitPrice()
                    ))
                    .toList()
            )
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }
    
    
}
