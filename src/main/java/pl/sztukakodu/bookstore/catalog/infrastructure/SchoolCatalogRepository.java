package pl.sztukakodu.bookstore.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.sztukakodu.bookstore.catalog.domain.Book;
import pl.sztukakodu.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SchoolCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public SchoolCatalogRepository() {
        storage.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1834));
        storage.put(2L, new Book(1L, "Ogniem i mieczem", "Henryk Sienkiewicz", 1884));
        storage.put(3L, new Book(1L, "Chłopi", "Władysław Reymont", 1904));
        storage.put(4L, new Book(1L, "Pan Wołodyjowski", "Henryk Sienkiewicz", 1904));
        storage.put(5L, new Book(5L, "Dziady", "Adam Mickiewicz", 2222));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
