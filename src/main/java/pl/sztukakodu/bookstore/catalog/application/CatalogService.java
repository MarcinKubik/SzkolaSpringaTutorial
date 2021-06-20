package pl.sztukakodu.bookstore.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.sztukakodu.bookstore.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookstore.catalog.domain.Book;
import pl.sztukakodu.bookstore.catalog.domain.CatalogRepository;
import pl.sztukakodu.bookstore.uploads.application.port.UploadUseCase;
import pl.sztukakodu.bookstore.uploads.domain.Upload;

import java.util.*;
import java.util.stream.Collectors;

import static pl.sztukakodu.bookstore.uploads.application.port.UploadUseCase.*;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;
    private final UploadUseCase upload;

    @Override
    public List<Book> findAll(){
        return repository.findAll();
    }

    @Override
    public List<Book> findListByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .filter(book -> book.getAuthor().toLowerCase().startsWith(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitle(String title){
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author){
        return repository.findAll()
                .stream()
                .filter(book -> book.getAuthor().toLowerCase().startsWith(author.toLowerCase()))
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
    public Book addBook(CreateBookCommand command){
        Book book = command.toBook();
        return repository.save(book);
    }

    @Override
    public void removeById(Long id){
        repository.removeById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
       return repository.findById(command.getId())
        .map(book -> {
            Book updatedBook = command.updateFields(book);
            repository.save(updatedBook);
            return UpdateBookResponse.SUCCESS;
        })
                .orElseGet(() -> new UpdateBookResponse(false, Collections.singletonList("Book not found with id: " + command.getId())));

    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command) {
        repository.findById(command.getId())
                .ifPresent(book ->{
                    Upload savedUpload = upload.save(new SaveUploadCommand(command.getFilename(), command.getFile(), command.getContentType()));
                    book.setCoverId(savedUpload.getId());
                    repository.save(book);
                        }

                );
    }

    @Override
    public void removeBookCover(Long id) {
        repository.findById(id).ifPresent(book -> {
            if (book.getCoverId() != null) {
                upload.removeById(book.getCoverId());
                book.setCoverId(null);
                repository.save(book);
            }
        });
    }

}
