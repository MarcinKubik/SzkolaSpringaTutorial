package pl.sztukakodu.bookstore.order.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder

public class Order {
    private Long id;
    @Builder.Default
    private OrderStatus status = OrderStatus.NEW;
    private List<OrderItem> items;
    private Recipient recipient;
    private LocalDateTime createdAt;

    public BigDecimal totalPrice(){
       return items.stream()
                .map(item -> item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())))
                //.reduce((p1, p2) -> p1.add(p2)).get(); //with lambda
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
