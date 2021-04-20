package pl.sztukakodu.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	private final CatalogService catalogService;

	public BookstoreApplication(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@Override
	public void run(String... args) throws Exception {

		List<Book> books = catalogService.findByTitle("Pan Tadeusz");
		books.forEach(System.out::println);
	}
}
