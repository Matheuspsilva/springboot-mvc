package com.matheussilvadev.springbootmvc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends  WebSecurityConfigurerAdapter{
	
	@Override //Configura as solicitações de acesso por http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() //Desativa configurações padrões de memória.
		.authorizeRequests() //Permitir/Restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() //Permitindo acesso à página inicial
		.anyRequest().authenticated()
		.and().formLogin().permitAll() // Permitindo qualquer usuário acessar formulário de login
		.and().logout()//Mapeia url de logout e invalida usuário autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override //Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())// Senha em texto puro
		.withUser("Matheus")
		.password("$2a$10$MFKOCoaiPrFGB/caDOun2OLQHE7cYNwhFunD0DlDR0Yfo3.dBZ.fa")//1234
		.roles("ADMIN");
	}
	
	@Override //Ignora URL's específicas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}
	
}