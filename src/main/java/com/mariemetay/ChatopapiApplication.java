package com.mariemetay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ChâTop API", version = "1.0", description = "Relationship between tenants and owners"))
@SecurityScheme(name = "Authorization", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT")
public class ChatopapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatopapiApplication.class, args);
	}

}
