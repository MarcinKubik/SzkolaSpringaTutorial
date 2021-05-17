package pl.sztukakodu.bookstore.catalog.application.port;

import lombok.Builder;
import lombok.Value;
import pl.sztukakodu.bookstore.catalog.domain.Book;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public interface CatalogUseCase {
    List<Book> findByTitle(String title);
    Optional<Book> findOneByTitle(String title);
    List<Book> findByAuthor(String author);

    List<Book> findAll();

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    void addBook(CreateBookCommand command);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    @Value  // from lombok, all fields private final, getters, setters, toString, constructor all args
    class CreateBookCommand {
        String title;
        String author;
        Integer year;
        BigDecimal price;

       public Book toBook(){
            return new Book(title, author, year, price);
        }
    }

    @Value
    @Builder
    class UpdateBookCommand{
        Long id;
        String title;
        String author;
        Integer year;
        BigDecimal price;

       public Book updateFields(Book book){
            if(title != null){
                book.setTitle(title);
            }
            if(author != null){
                book.setAuthor(author);
            }
            if (year != null){
                book.setYear(year);
            }
            if(price != null){
                book.setPrice(book.getPrice());
            }
            return book;
        }
    }

    @Value
    class UpdateBookResponse{
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, emptyList());
        boolean success;
        List<String> errors;
    }
}
