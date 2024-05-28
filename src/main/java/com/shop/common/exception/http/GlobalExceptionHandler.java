package com.shop.common.exception.http;

import java.io.FileNotFoundException;

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
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler({HttpException.class})
	public ResponseEntity<ErrorResponse> httpException(HttpException ex) {
		log.error("http error", ex);
		ErrorResponse response = new ErrorResponse(ex.getStatus().value(), ex.getMessage());
		return ResponseEntity.status(ex.getStatus()).body(response);
	}

	@ExceptionHandler({FileNotFoundException.class})
	public ResponseEntity<ErrorResponse> fileNotFoundException(HttpException ex) {
		ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> exception(Exception ex) {
		log.error("exception", ex);
		ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
