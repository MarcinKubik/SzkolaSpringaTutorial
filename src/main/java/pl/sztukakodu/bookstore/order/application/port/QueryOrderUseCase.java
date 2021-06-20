package pl.sztukakodu.bookstore.order.application.port;

import pl.sztukakodu.bookstore.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {
    List<Order> findAll();
    Optional<Order> findById(Long id);
}
