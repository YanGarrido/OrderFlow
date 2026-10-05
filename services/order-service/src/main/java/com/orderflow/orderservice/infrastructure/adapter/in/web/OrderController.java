package com.orderflow.orderservice.infrastructure.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderflow.orderservice.application.exception.OrderNotFoundException;
import com.orderflow.orderservice.application.port.in.CancelOrderUseCase;
import com.orderflow.orderservice.application.port.in.CreateOrderCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderItemCommand;
import com.orderflow.orderservice.application.port.in.CreateOrderUseCase;
import com.orderflow.orderservice.application.port.in.FindOrderByIdUseCase;
import com.orderflow.orderservice.domain.model.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Tag (
    name = "Orders",
    description = "Operações relacionadas a pedidos"
)
@RestController 
@RequestMapping ("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, FindOrderByIdUseCase findOrderByIdUseCase, CancelOrderUseCase cancelOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
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

    @Operation (
        summary = "Busca um pedido",
        description = "Busca um pedido pelo seu identificador"
    )
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Pedido encontrado"
        ),
        @ApiResponse (
                responseCode = "400",
                description = "Identificador inválido"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Pedido não encontrado"
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable UUID id) {
        Order order = findOrderByIdUseCase.execute(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @Operation (
        summary = "Cancela um pedido",
        description = "Cancela um pedido que esteja no estado CREATED"
    )
    @ApiResponses ({
        @ApiResponse (
            responseCode = "200",
            description = "Pedido cancelado"
        ),
        @ApiResponse (
            responseCode = "404",
            description = "Pedido não encontrado"
        ),
        @ApiResponse (
            responseCode = "409",
            description = "Pedido não pode ser cancelado"
        )
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(@PathVariable UUID id) {
       Order order = cancelOrderUseCase.execute(id);
       return ResponseEntity.ok(OrderResponse.from(order));
    }
    
    
    
}
