package pl.sztukakodu.bookstore.catalog.domain;

import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase.UpdateBookResponse;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository {
    List<Book> findAll();

    Book save(Book book);

    Optional<Book> findById(Long id);

    void removeById(Long id);
}
