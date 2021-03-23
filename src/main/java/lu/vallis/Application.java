package lu.vallis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "lu.vallis.repository")
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
