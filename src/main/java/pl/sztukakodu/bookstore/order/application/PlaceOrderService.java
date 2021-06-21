package pl.sztukakodu.bookstore.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase;
import pl.sztukakodu.bookstore.order.domain.Order;
import pl.sztukakodu.bookstore.order.domain.OrderRepository;
import pl.sztukakodu.bookstore.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

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
        if(save == null){
            return PlaceOrderResponse.failure("Your order couldn't be created");
        }
        return PlaceOrderResponse.success(save.getId());
    }

    @Override
    public UpdateOrderResponse updateOrder(UpdateOrderCommand command) {
        return repository
                .findById(command.getId())
                .map(order -> {
                    Order updatedOrder = command.updateFields(order);
                    repository.save(updatedOrder);
                    return new UpdateOrderResponse(true, Collections.emptyList());
                })
                .orElseGet(() -> new UpdateOrderResponse(false, Collections.singletonList("Order not found with id: " + command.getId())));
    }

    @Override
    public void removeById(Long id){
        repository.removeById(id);
    }
}
