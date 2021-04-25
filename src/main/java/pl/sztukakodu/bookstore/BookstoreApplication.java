package pl.sztukakodu.bookstore;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import pl.sztukakodu.bookstore.catalog.domain.CatalogRepository;
import pl.sztukakodu.bookstore.catalog.infrastructure.BestsellerCatalogRepository;
import pl.sztukakodu.bookstore.catalog.infrastructure.SchoolCatalogRepository;
import java.util.Random;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);

	}

	/*@Bean
	CatalogRepository catalogRepository(){

		Random random = new Random();
		if(random.nextInt() % 2 == 0){
			System.out.println("Wylosowano SchoolCatalogRepository");
			return new SchoolCatalogRepository();
		}else {
			System.out.println("Wylosowano BestsellerCatalogRepository");
			return new BestsellerCatalogRepository();
		}
	}*/


}
