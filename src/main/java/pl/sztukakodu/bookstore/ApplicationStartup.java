package pl.sztukakodu.bookstore;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookstore.catalog.application.CatalogController;
import pl.sztukakodu.bookstore.catalog.domain.Book;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;
    private final String title;
    private final Long limit;
    private final String author;

    public ApplicationStartup(CatalogController catalogController,
                              @Value("${bookstore.catalog.query}") String title,
                              @Value("${bookstore.catalog.limit}") Long limit,
                              @Value("${bookstore.catalog.author}") String author) {
        this.catalogController = catalogController;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Book> books = catalogController.findByTitle(title);
        System.out.println("Books by title: " + title);
        books.stream().limit(limit).forEach(System.out::println);

        List<Book> booksByAuthor = catalogController.findByAuthor(author);
        System.out.println("Books by author: " + author);
        booksByAuthor.forEach(System.out::println);


    }
}
