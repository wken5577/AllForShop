package com.shop.common.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@Import({LoginApiConfig.class})
@RequiredArgsConstructor
public class SwaggerConfig {

	private final Paths loginPaths;
	@Bean
	public OpenAPI springShopOpenAPI() {
		String xsrfSchemeName = "xsrfToken";

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList(xsrfSchemeName);

		Components components = new Components()
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
			.components(components)
			.paths(loginPaths);
	}

}
