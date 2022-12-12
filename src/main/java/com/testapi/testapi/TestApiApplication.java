package com.testapi.testapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class TestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApiApplication.class, args);
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(
						new ApiInfoBuilder()
						.title("Mini Autorizador")
						.description("Mini Autorizador")
						.build()
				)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.build()
				.pathMapping("/");
	}
}
