package com.cetes.directo.calculadoras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class CalculadorasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculadorasApplication.class, args);
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
		properties.setLocation(new FileSystemResource("/opt/calculadoras/config.properties"));
		properties.setIgnoreResourceNotFound(false);

		return properties;
	}
}
