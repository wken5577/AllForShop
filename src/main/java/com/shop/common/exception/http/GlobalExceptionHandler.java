package com.shop.common.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shop.common.exception.http.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({BindException.class})
	public ResponseEntity<ErrorResponse> validException(BindException ex) {
		log.error("bind error", ex.getBindingResult().getAllErrors()
			.stream().findFirst().get().getDefaultMessage());
		ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
			ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({HttpException.class})
	public ResponseEntity<ErrorResponse> httpException(HttpException ex) {
		log.error("http error", ex);
		ErrorResponse response = new ErrorResponse(ex.getStatus().value(), ex.getMessage());
		return new ResponseEntity<>(response, ex.getStatus());
	}


	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> exception(Exception ex) {
		log.error("exception", ex);
		ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
