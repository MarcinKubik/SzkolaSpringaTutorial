package pl.sztukakodu.bookstore.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookstore.catalog.domain.Book;
import pl.sztukakodu.bookstore.catalog.domain.CatalogRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;

    @Override
    public List<Book> findAll(){
        return repository.findAll();
    }

    @Override
    public List<Book> findByTitle(String title){
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author){
        return repository.findAll()
                .stream()
                .filter(book -> book.getAuthor().startsWith(author))
                .collect(Collectors.toList());
    }



    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author){
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .filter(book -> book.getAuthor().startsWith(author))
                .findFirst();
    }

    @Override
    public void addBook(CreateBookCommand command){
        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear());
        repository.save(book);
    }

    @Override
    public void removeById(Long id){

    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        repository.findById(command.getId())
        .map(book -> {
            book.setTitle(command.getTitle());
            book.setAuthor(command.getAuthor());
            book.setYear(command.getYear());
            return UpdateBookResponse.SUCCESS;
        })
            .orElseGet(() -> new UpdateBookResponse(false, Arrays.asList("Book not found")));

        return null;
    }

}