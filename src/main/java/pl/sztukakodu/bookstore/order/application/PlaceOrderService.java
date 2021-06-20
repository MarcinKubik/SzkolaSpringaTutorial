package pl.sztukakodu.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase;
import pl.sztukakodu.bookstore.order.domain.Order;
import pl.sztukakodu.bookstore.order.domain.OrderRepository;
import pl.sztukakodu.bookstore.order.domain.OrderStatus;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {
    private final OrderRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }
}
