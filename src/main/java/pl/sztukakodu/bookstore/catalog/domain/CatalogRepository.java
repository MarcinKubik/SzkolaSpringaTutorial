package pl.sztukakodu.bookstore.catalog.domain;

import java.util.List;

public interface CatalogRepository {

    List<Book> findAll();
}
