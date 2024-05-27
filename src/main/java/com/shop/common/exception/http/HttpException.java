package com.shop.common.exception.http;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class HttpException extends RuntimeException{

	private final HttpStatus status;
	private final String message;

	public HttpException(HttpStatus status, String message) {
		super(message);
		this.status = status;
		this.message = message;
	}
}
