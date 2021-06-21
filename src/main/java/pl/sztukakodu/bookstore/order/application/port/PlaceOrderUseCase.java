package pl.sztukakodu.bookstore.order.application.port;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import pl.sztukakodu.bookstore.order.domain.Order;
import pl.sztukakodu.bookstore.order.domain.OrderItem;
import pl.sztukakodu.bookstore.order.domain.OrderStatus;
import pl.sztukakodu.bookstore.order.domain.Recipient;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public interface PlaceOrderUseCase {
    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    UpdateOrderResponse updateOrder(UpdateOrderCommand command);

    void removeById(Long id);

    @Builder
    @Value
    class PlaceOrderCommand {
        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    @Value
    class PlaceOrderResponse {
        boolean success;
        Long orderId;
        List<String> errors;

        public static PlaceOrderResponse success(Long orderId) {
            return new PlaceOrderResponse(true, orderId, emptyList());
        }

        public static PlaceOrderResponse failure(String ... errors){
            return new PlaceOrderResponse(false, null, Arrays.asList(errors));
        }
    }

    @Value
    @Builder
    class UpdateOrderCommand{
        Long id;
        OrderStatus status;
        List<OrderItem> items;
        Recipient recipient;

        public Order updateFields(Order order){
        if(status != null){
            order.setStatus(status);
        }

        if(items != null){
            order.setItems(items);
        }

        if(recipient != null){
            order.setRecipient(recipient);
        }

        return order;
        }
    }

    @Value
    class UpdateOrderResponse{
        boolean success;
        List<String> errors;
    }

}
