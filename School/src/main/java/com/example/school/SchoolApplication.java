package com.example.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}
	   @Bean
	   public Docket productApi() {
	      return new Docket(DocumentationType.SWAGGER_2).select()
	         .apis(RequestHandlerSelectors.basePackage("com.example.school")).build();
	   }
}
