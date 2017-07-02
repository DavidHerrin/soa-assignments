package com.cooksys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class EntityIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityIntegrationApplication.class, args);
	}
}
