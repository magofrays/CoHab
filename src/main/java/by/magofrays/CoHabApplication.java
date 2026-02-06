package by.magofrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
public class CoHabApplication {

	public static void main(String[] args) {

		SpringApplication.run(CoHabApplication.class, args);
		log.info("Server running on: http//localhost:8080");
	}

}
