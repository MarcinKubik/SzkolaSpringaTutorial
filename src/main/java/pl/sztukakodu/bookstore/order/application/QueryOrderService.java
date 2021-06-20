package pl.sztukakodu.bookstore.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookstore.order.application.port.QueryOrderUseCase;
import pl.sztukakodu.bookstore.order.domain.Order;
import pl.sztukakodu.bookstore.order.domain.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {
    private final OrderRepository repository;

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }
}
