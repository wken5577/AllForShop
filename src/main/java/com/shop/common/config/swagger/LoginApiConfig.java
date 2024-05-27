package com.shop.common.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class LoginApiConfig {

	@Bean
	public Paths loginPaths() {
		Paths paths = new Paths();
		paths.addPathItem("/login", createLoginPathItem());
		return paths;
	}

	private PathItem createLoginPathItem() {
		Operation operation = new Operation()
			.summary("User login")
			.description("User provides username and password to login")
			.requestBody(createRequestBody())
			.responses(createApiResponses());

		return new PathItem()
			.post(operation);
	}

	private RequestBody createRequestBody() {
		Schema<?> schema = new Schema<>()
			.type("object")
			.addProperties("username", new Schema<>().type("string").description("Username for login"))
			.addProperties("password", new Schema<>().type("string").description("Password for login"));

		Content content = new Content()
			.addMediaType("application/json", new MediaType().schema(schema));

		return new RequestBody()
			.content(content)
			.required(true);
	}

	private ApiResponses createApiResponses() {
		ApiResponses responses = new ApiResponses();
		ApiResponse response = new ApiResponse()
			.description("Successful login")
			.content(
				new Content().addMediaType("application/json", new MediaType().schema(new Schema<>().type("string"))));
		responses.addApiResponse("200", response);
		return responses;
	}
}

