package pl.sztukakodu.bookstore.catalog.infrastructure;

import org.springframework.stereotype.Repository;
import pl.sztukakodu.bookstore.catalog.domain.Book;
import pl.sztukakodu.bookstore.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BestsellerCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public BestsellerCatalogRepository() {
        storage.put(1L, new Book(1L, "Harry Potter", "JK Rowling", 1834));
        storage.put(2L, new Book(1L, "Władca Pierścieni", "Henryk Sienkiewicz", 1884));
        storage.put(3L, new Book(1L, "Mężczyźni którzy nienawidzą kobiet", "Władysław Reymont", 1904));
        storage.put(4L, new Book(1L, "Sezon burz", "Henryk Sienkiewicz", 1904));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
