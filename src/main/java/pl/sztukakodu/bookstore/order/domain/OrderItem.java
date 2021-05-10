package pl.sztukakodu.bookstore.order.domain;

import lombok.Value;
import pl.sztukakodu.bookstore.catalog.domain.Book;

@Value
public class OrderItem {
    Book book;
    int quantity;
}
