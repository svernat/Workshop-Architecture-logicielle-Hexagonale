package com.sve.workshops.hexagonal.exercice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(
		considerNestedRepositories = true
)
public class HexagonalApplication {

	static void main(String[] args) {
		SpringApplication.run(HexagonalApplication.class, args);
	}
}