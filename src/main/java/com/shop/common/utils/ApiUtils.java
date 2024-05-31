package com.shop.common.utils;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiUtils {
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public <T> ResponseEntity<T> apiCall(String url, Class<T> responseType, HttpHeaders headers,
		Object bodyJson, HttpMethod method) {
		String contentBody = null;
		try {
			contentBody = objectMapper.writeValueAsString(bodyJson);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		System.out.println("contentBody = " + contentBody);
		HttpEntity<String> request = new HttpEntity<>(contentBody, headers);
		return restTemplate.exchange(url, method, request, responseType);
	}
}
