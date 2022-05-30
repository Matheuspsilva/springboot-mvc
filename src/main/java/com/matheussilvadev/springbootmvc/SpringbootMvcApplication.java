package com.matheussilvadev.springbootmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "com.matheussilvadev.springbootmvc.model")
@ComponentScan(basePackages = {"com.matheussilvadev.springbootmvc.*"})
@EnableJpaRepositories(basePackages = {"com.matheussilvadev.springbootmvc.repository"})
@EnableTransactionManagement
@EnableWebMvc
public class SpringbootMvcApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMvcApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("/login");
		registry.addViewController("/").setViewName("/index");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}
	
	 @Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 registry.addResourceHandler("/webjars/**", "/resources/**", "/static/**", "/img/**", "/css/**", "/js/**",
					"classpath:/static/", "classpath:/resources/")
			.addResourceLocations("/webjars/", "/resources/",
							"classpath:/static/**", "classpath:/static/img/**", "classpath:/static/",
							"classpath:/resources/", "classpath:/static/css/", "classpath:/static/js/", "/resources/**",
							"/WEB-INF/classes/static/**");
			
	  }


}
