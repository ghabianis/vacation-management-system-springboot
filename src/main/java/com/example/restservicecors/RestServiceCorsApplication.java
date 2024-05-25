package com.example.restservicecors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import net.sf.jsqlparser.Model;

@SpringBootApplication
@Controller
public class RestServiceCorsApplication {

	@GetMapping("/")
	public String index(final Model model) {
		return "signin";
	}

	public static void main(String[] args) {
		SpringApplication.run(RestServiceCorsApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOriginPatterns("*") // Change allowedOrigins to allowedOriginPatterns
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.exposedHeaders("Authorization", "api_key", "Content-Type")
						.allowCredentials(true)
						.maxAge(3600);
			}
		};
	}

	@Bean
	public OpenAPI openapi() {
		// Define the server URL using HTTPS
		Server server = new Server()
				.url("http://localhost:8080")
				.description("HTTPS server");

		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new Components()
						.addSecuritySchemes("bearerAuth",
								new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")))
				.info(new Info()
						.title("SpringBoot API")
						.description("Spring Boot REST API")
						.version("3.0"))
				// Add the HTTPS server to the list of servers
				.addServersItem(server);
	}

}
