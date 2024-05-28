package com.shop.common.exception.http.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
	private final int statusCode;
	private final String message;

}
