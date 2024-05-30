package com.shop.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@ConfigurationProperties(prefix = "cors")
@Getter
public class CorsProperties {
	private final String allowedOrigins;
	private final String allowedMethods;
	private final String allowedHeaders;
	private final Long maxAge;

	@ConstructorBinding
	public CorsProperties(String allowedOrigins, String allowedMethods, String allowedHeaders, Long maxAge) {
		this.allowedOrigins = allowedOrigins;
		this.allowedMethods = allowedMethods;
		this.allowedHeaders = allowedHeaders;
		this.maxAge = maxAge;
	}
}
