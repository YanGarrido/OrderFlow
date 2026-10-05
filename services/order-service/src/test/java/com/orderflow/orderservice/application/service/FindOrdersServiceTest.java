package com.orderflow.orderservice.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.orderflow.orderservice.application.port.in.FindOrdersQuery;
import com.orderflow.orderservice.application.port.in.OrderPage;
import com.orderflow.orderservice.application.port.out.OrderRepository;
import com.orderflow.orderservice.domain.model.Order;

class FindOrdersServiceTest {

    @Test
    void shouldFindOrdersUsingPaginationQuery() {
        OrderRepository repository = mock(OrderRepository.class);

        Order order = Order.create(UUID.randomUUID());

        FindOrdersQuery query = new FindOrdersQuery(0, 20);

        OrderPage expectedPage = new OrderPage(
                List.of(order),
                0,
                20,
                1,
                1
        );

        when(repository.findAll(query))
                .thenReturn(expectedPage);

        FindOrdersService service =
                new FindOrdersService(repository);

        OrderPage result = service.execute(query);

        assertEquals(expectedPage, result);
        verify(repository).findAll(query);
    }
}