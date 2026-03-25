package com.kodnest.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.kodnest.app.filters.AuthenticationFilter;

@SpringBootApplication
public class KartnovaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KartnovaApplication.class, args);
	}
	
}
