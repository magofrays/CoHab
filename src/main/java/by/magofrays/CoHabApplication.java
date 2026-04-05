package by.magofrays;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class CoHabApplication {

	public static void main(String[] args) {

		SpringApplication.run(CoHabApplication.class, args);
	}

}
