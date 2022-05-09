package com.matheussilvadev.springbootmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.matheussilvadev.springbootmvc.model")
@ComponentScan(basePackages = {"com.matheussilvadev.springbootmvc.*"})
@EnableJpaRepositories(basePackages = {"com.matheussilvadev.springbootmvc.repository"})
@EnableTransactionManagement
public class SpringbootMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMvcApplication.class, args);
	}

}
