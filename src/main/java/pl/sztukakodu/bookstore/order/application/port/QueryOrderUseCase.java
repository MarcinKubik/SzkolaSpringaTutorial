package pl.sztukakodu.bookstore.order.application.port;

import pl.sztukakodu.bookstore.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
