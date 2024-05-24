package com.shop.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI springShopOpenAPI() {
		String sessionSchemeName = "cookieAuth";
		String xsrfSchemeName = "xsrfToken";

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList(sessionSchemeName)
			.addList(xsrfSchemeName);

		Components components = new Components()
			.addSecuritySchemes(sessionSchemeName, new SecurityScheme()
				.name(sessionSchemeName)
				.type(SecurityScheme.Type.APIKEY)
				.in(SecurityScheme.In.COOKIE)
				.name("JSESSIONID"))
			.addSecuritySchemes(xsrfSchemeName, new SecurityScheme()
				.name(xsrfSchemeName)
				.type(SecurityScheme.Type.APIKEY)
				.in(SecurityScheme.In.HEADER)
				.name("X-XSRF-TOKEN"));

		Server server = new Server();
		server.setUrl("/");
		server.setDescription("Test Server URL");

		return new OpenAPI()
			.info(new Info().title("ALL FOR SHOP V1 API")
				.version("v1.0.0"))
			.addServersItem(server)
			.addSecurityItem(securityRequirement)
			.components(components);
	}
}
