package pl.sztukakodu.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
