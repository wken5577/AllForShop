package com.shop.security.entrypoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		authException.printStackTrace();
		log.info("Responding with unauthorized error. Message := {}", authException.getMessage());
		log.info("request url := {}", request.getRequestURI());
		response.sendError(
			HttpServletResponse.SC_UNAUTHORIZED,
			authException.getLocalizedMessage()
		);
	}
}

