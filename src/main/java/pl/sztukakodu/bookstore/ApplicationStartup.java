package pl.sztukakodu.bookstore;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase.CreateBookCommand;
import pl.sztukakodu.bookstore.catalog.domain.Book;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final Long limit;
    private final String author;

    public ApplicationStartup(CatalogUseCase catalog,
                              @Value("${bookstore.catalog.query}") String title,
                              @Value("${bookstore.catalog.limit}") Long limit,
                              @Value("${bookstore.catalog.author}") String author) {
        this.catalog = catalog;
        this.title = title;
        this.limit = limit;
        this.author = author;
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
        findByTitle();
        findAndUpdate();
        findByTitle();
    }


    private void initData() {
        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1834));
        catalog.addBook(new CreateBookCommand("Ogniem i mieczem", "Henryk Sienkiewicz", 1884));
        catalog.addBook(new CreateBookCommand("Chłopi", "Władysław Reymont", 1904));
        catalog.addBook(new CreateBookCommand("Pan Wołodyjowski", "Henryk Sienkiewicz", 1899));
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
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz");
    }
}
