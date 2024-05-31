package com.shop.payment.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.common.annotation.PreventDuplicate;
import com.shop.common.config.properties.PaymentProperties;
import com.shop.common.exception.http.BadRequestException;
import com.shop.common.utils.ApiUtils;
import com.shop.order.service.OrderService;
import com.shop.payment.controller.request.PaymentConfirmReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentProperties paymentProperties;

	private final OrderService orderService;
	private final ApiUtils apiUtils;

	@PostMapping("/payment/confirm")
	@PreventDuplicate
	public ResponseEntity<Void> conformPayment(@RequestBody PaymentConfirmReqDto reqDto) {
		orderService.checkOrderAmount(reqDto.getOrderId(), reqDto.getAmount());
		String authorizations = createAuthorizationHeader();

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authorizations);
		headers.set("Content-Type", "application/json");

		ResponseEntity<String> response = apiUtils.apiCall(paymentProperties.getUrl(), String.class,
			headers, reqDto, HttpMethod.POST);

		if (response.getStatusCode().is2xxSuccessful()) {
			orderService.paymentComplete((reqDto.getOrderId()));
			return ResponseEntity.ok().build();
		}
		throw new BadRequestException("결제 실패");
	}

	private String createAuthorizationHeader() {
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((paymentProperties.getSecretKey() + ":")
			.getBytes(StandardCharsets.UTF_8));
		return "Basic " + new String(encodedBytes);
	}
}
