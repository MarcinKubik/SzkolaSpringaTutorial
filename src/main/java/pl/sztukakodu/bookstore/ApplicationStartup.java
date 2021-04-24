package pl.sztukakodu.bookstore;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookstore.catalog.application.CatalogController;
import pl.sztukakodu.bookstore.catalog.domain.Book;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;

    @Override
    public void run(String... args) throws Exception {

        List<Book> books = catalogController.findByTitle("Pan");
        books.forEach(System.out::println);
    }
}
