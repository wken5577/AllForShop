package com.shop.common.exception.http.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorResponse {
	private final int statusCode;
	private final String message;

}
