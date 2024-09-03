package com.hmb.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("SPRING_JPA_HIBERNATE_DDL_AUTO", dotenv.get("SPRING_JPA_HIBERNATE_DDL_AUTO"));
        System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        System.setProperty("SPRING_DATASOURCE_DRIVER_CLASS_NAME", dotenv.get("SPRING_DATASOURCE_DRIVER_CLASS_NAME"));
        System.setProperty("SPRING_JPA_SHOW_SQL", dotenv.get("SPRING_JPA_SHOW_SQL"));
        System.setProperty("server.port", "9090");

		SpringApplication.run(ProjectApplication.class, args);
	}
}
