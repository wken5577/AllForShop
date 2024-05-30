package com.shop.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {
	private final String secretKey;
	private final String url;

	@ConstructorBinding
	public PaymentProperties(String secretKey, String url) {
		this.secretKey = secretKey;
		this.url = url;
	}
}
