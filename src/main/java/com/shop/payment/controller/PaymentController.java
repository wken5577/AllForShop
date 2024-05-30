package com.shop.payment.controller;

import static javax.crypto.Cipher.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.common.config.properties.PaymentProperties;
import com.shop.payment.controller.request.PaymentConfirmReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentProperties paymentProperties;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	@PostMapping("/payment/confirm")
	public ResponseEntity<Void> conformPayment(@RequestBody PaymentConfirmReqDto reqDto) throws
		JsonProcessingException {

		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((paymentProperties.getSecretKey() + ":")
			.getBytes(StandardCharsets.UTF_8));
		String authorizations = "Basic " + new String(encodedBytes);

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authorizations);
		headers.set("Content-Type", "application/json");

		String body = objectMapper.writeValueAsString(reqDto);
		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(
			paymentProperties.getUrl(), HttpMethod.POST, entity, String.class
		);
		System.out.println("response = " + response);
		if (response.getStatusCode().is2xxSuccessful()) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
