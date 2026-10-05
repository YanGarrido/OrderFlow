package com.orderflow.orderservice.application.port.in;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FindOrdersQueryTest {

    @Test
    void shouldAcceptValidPagination() {
        assertDoesNotThrow(
                () -> new FindOrdersQuery(0, 20)
        );
    }

    @Test
    void shouldRejectNegativePage() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FindOrdersQuery(-1, 20)
        );
    }

    @Test
    void shouldRejectPageSizeGreaterThanLimit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FindOrdersQuery(0, 101)
        );
    }

    @Test
    void shouldRejectZeroPageSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FindOrdersQuery(0, 0)
        );
    }
}