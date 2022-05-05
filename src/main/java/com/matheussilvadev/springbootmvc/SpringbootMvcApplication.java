package com.matheussilvadev.springbootmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.matheussilvadev.springbootmvc.model")
public class SpringbootMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMvcApplication.class, args);
	}

}
