package com.orderflow.orderservice.infrastructure.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "OrderFlow Order Service API",
        version = "v1",
        description = "API responsável pelo gerenciamento de pedidos"
    )
)
public class OpenApiConfiguration {

    
}
