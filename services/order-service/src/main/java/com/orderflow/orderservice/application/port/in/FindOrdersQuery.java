package com.orderflow.orderservice.application.port.in;

public record FindOrdersQuery(
    int page,
    int size
) {
    public FindOrdersQuery {
        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page must be greater than or equal to zero"
            );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100"
            );
        }
    }
}
