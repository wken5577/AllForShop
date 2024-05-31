package com.shop.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@ConfigurationProperties(prefix = "redis")
@Getter
public class RedisProperties {
	private final String host;
	private final int port;

	@ConstructorBinding
	public RedisProperties(String host, int port) {
		this.host = host;
		this.port = port;
	}
}
