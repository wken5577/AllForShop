package com.shop.payment.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.common.config.properties.PaymentProperties;
import com.shop.common.utils.ApiUtils;
import com.shop.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TossPaymentService implements PaymentService {
	private static final String PAYMENT_SUFFIX = "/payments/confirm";
	private static final String CANCEL_SUFFIX = "/payments/{paymentKey}/cancel";

	/* 멱등키 헤더 */
	private static final String IDEMPOTENCY_KEY = "Idempotency-Key";
	private final ApiUtils apiUtils;
	private final PaymentProperties paymentProperties;
	private final OrderService orderService;

	@Override
	public boolean confirmPayment(UUID orderId, String paymentKey, int amount) {
		HttpHeaders headers = paymentsRequestHeader();

		Map<String, Object> body = new HashMap<>();
		body.put("orderId", orderId.toString());
		body.put("paymentKey", paymentKey);
		body.put("amount", amount);

		ResponseEntity<String> response = apiUtils.apiCall(paymentProperties.getUrl() + PAYMENT_SUFFIX,
			String.class, headers, body, HttpMethod.POST);

		if (response.getStatusCode().is2xxSuccessful()) {
			orderService.paymentComplete(orderId, paymentKey);
			return true;
		}
		return false;
	}

	@Override
	public boolean cancelPayment(UUID orderId, String paymentKey, String cancelReason) {
		String url = (paymentProperties.getUrl() + CANCEL_SUFFIX)
			.replace("{paymentKey}", paymentKey);
		HttpHeaders headers = paymentsRequestHeader();
		// 명등키 헤더 추가
		headers.set(IDEMPOTENCY_KEY, orderId.toString());

		Map<String, Object> body = new HashMap<>();
		body.put("cancelReason", cancelReason);

		ResponseEntity<String> response = apiUtils.apiCall(url, String.class, headers,
			body, HttpMethod.POST);
		if (response.getStatusCode().is2xxSuccessful()) {
			orderService.paymentCancel(orderId);
			return true;
		}
		return false;
	}

	private HttpHeaders paymentsRequestHeader() {
		String authorizations = createAuthorizationHeader();

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, authorizations);
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

	private String createAuthorizationHeader() {
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((paymentProperties.getSecretKey() + ":")
			.getBytes(StandardCharsets.UTF_8));
		return "Basic " + new String(encodedBytes);
	}
}
