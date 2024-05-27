package com.shop.common.exception.http;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException{

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
