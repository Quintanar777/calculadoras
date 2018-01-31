package com.cetes.directo.calculadoras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class CalculadorasApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CalculadorasApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CalculadorasApplication.class, args);
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
		properties.setLocation(new FileSystemResource("/oracle2/svd/calculadoras/config.properties"));
		properties.setIgnoreResourceNotFound(false);

		return properties;
	}
}
