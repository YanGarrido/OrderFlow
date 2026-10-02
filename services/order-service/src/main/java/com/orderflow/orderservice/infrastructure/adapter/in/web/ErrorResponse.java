package com.orderflow.orderservice.infrastructure.adapter.in.web;

public record ErrorResponse(
    String code,
    String message
) {
    
}
