package pl.sztukakodu.bookstore;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.sztukakodu.bookstore.catalog.domain.Book;
import pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase;
import pl.sztukakodu.bookstore.order.application.port.QueryOrderUseCase;
import pl.sztukakodu.bookstore.order.domain.OrderItem;
import pl.sztukakodu.bookstore.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

import static pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase.*;
import static pl.sztukakodu.bookstore.order.application.port.PlaceOrderUseCase.*;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final Long limit;
    private final String author;

    public ApplicationStartup(CatalogUseCase catalog,
                              PlaceOrderUseCase placeOrder,
                              QueryOrderUseCase queryOrder,
                              @Value("${bookstore.catalog.query}") String title,
                              @Value("${bookstore.catalog.limit}") Long limit,
                              @Value("${bookstore.catalog.author}") String author) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book panTadeusz = catalog.findOneByTitle("Pan Tadeusz").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book chlopi = catalog.findOneByTitle("Chłopi").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        Recipient recipient = Recipient.
                builder()
                .name("Jan Kowalski")
                .phone("123-456-789")
                .street("Armii Krajowej 31")
                .city("Krakow")
                .zipCode("30-150")
                .email("jan@example.org")
                .build();
        PlaceOrderCommand command= PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(panTadeusz, 16))
                .item(new OrderItem(chlopi, 7))
                .build();

        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created order with id: " + response.getOrderId());

        queryOrder.findAll().forEach(order -> {
            System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS: " + order);
        });
    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }


    private void initData() {
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834, new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand("Ogniem i mieczem", "Henryk Sienkiewicz", 1884, new BigDecimal("29.90")));
        catalog.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904, new BigDecimal("11.90")));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1899, new BigDecimal("14.90")));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        System.out.println("Books by title: " + title);
        books.stream().limit(limit).forEach(System.out::println);

        List<Book> booksByAuthor = catalog.findByAuthor(author);
        System.out.println("Books by author: " + author);
        booksByAuthor.forEach(System.out::println);
    }


    private void findAndUpdate() {
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand updateBookCommand = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Pan Tadeusz, czyli Ostatni Zajazd na Litwie")
                            .build();
                    UpdateBookResponse response = catalog.updateBook(updateBookCommand);
                    System.out.println("Updating book result: " + response.isSuccess());
                });


    }
}
