package com.shop.security.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final ObjectMapper objectMapper;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		if (request.getMethod().equals("POST") && request.getContentType().equals("application/json")) {
			try {

				Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
				String username = requestBody.get("username");
				String password = requestBody.get("password");

				UsernamePasswordAuthenticationToken authRequest =
					new UsernamePasswordAuthenticationToken(username, password);
				setDetails(request, authRequest);
				return this.getAuthenticationManager().authenticate(authRequest);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return super.attemptAuthentication(request, response);
	}
}
