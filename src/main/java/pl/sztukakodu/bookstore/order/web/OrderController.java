package pl.sztukakodu.bookstore.order.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase.UpdateOrderCommand;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase.UpdateOrderResponse;
import pl.sztukakodu.bookstore.order.application.port.QueryOrderUseCase;
import pl.sztukakodu.bookstore.order.domain.Order;
import pl.sztukakodu.bookstore.order.domain.OrderItem;
import pl.sztukakodu.bookstore.order.domain.OrderStatus;
import pl.sztukakodu.bookstore.order.domain.Recipient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAll() {
        return queryOrder.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return queryOrder
                .findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> placeOrder(@Valid @RequestBody RestOrderCommand command){
        PlaceOrderResponse response = placeOrder.placeOrder(command.toPlaceOrderCommand());
        if (response.isSuccess()) {
            Order order = queryOrder.findById(response.getOrderId()).get();
            return ResponseEntity.created(createOrderUri(order)).build();
        } else{
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrder(@PathVariable Long id, @RequestBody RestOrderCommand command){
        UpdateOrderResponse response = placeOrder.updateOrder(command.toUpdateOrderCommand(id));
        if(!response.isSuccess()){
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        placeOrder.removeById(id);
    }

    private URI createOrderUri(Order order){
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + order.getId().toString()).build().toUri();
    }

    @Data
    private static class RestOrderCommand{
        @NotNull
        private List<OrderItem> items;

        @NotNull
        private Recipient recipient;

        private OrderStatus status;


        PlaceOrderCommand toPlaceOrderCommand(){
            return PlaceOrderCommand
                    .builder()
                    .items(items)
                    .recipient(recipient)
                    .build();
        }

        UpdateOrderCommand toUpdateOrderCommand(Long id){
            return UpdateOrderCommand
                    .builder()
                    .id(id)
                    .items(items)
                    .status(status)
                    .recipient(recipient)
                    .build();
        }
    }
}
