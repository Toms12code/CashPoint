package com.cashpoint.bck.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.cashpoint.bck")
@EnableJpaRepositories(basePackages = "com.cashpoint.bck.persistencia.repositorios")
@EntityScan(basePackages = "com.cashpoint.bck.persistencia.entidades")
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}
