package pl.sztukakodu.bookstore.catalog.application.port;

import lombok.AllArgsConstructor;
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

    Optional<Book> findById(Long id);

    Optional<Book> findOneByTitle(String title);

    List<Book> findByAuthor(String autor);

    List<Book> findAll();

    List<Book> findListByTitleAndAuthor(String title, String author);

    Optional<Book> findOneByTitleAndAuthor(String title, String author);

    Book addBook(CreateBookCommand command);

    void removeById(Long id);

    UpdateBookResponse updateBook(UpdateBookCommand command);

    void updateBookCover(UpdateBookCoverCommand command);

    @Value
    class UpdateBookCoverCommand{
        Long id;
        byte[] file;
        String contentType; //kind of file
        String filename;

    }

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
    @AllArgsConstructor
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
                book.setPrice(price);
            }
            return book;
        }
    }

    @Value
    class UpdateBookResponse{
        public static UpdateBookResponse SUCCESS = new UpdateBookResponse(true, emptyList());
        boolean Success;
        List<String> errors;
    }
}
