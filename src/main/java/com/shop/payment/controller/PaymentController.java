package com.shop.payment.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.common.annotation.PreventDuplicate;
import com.shop.common.config.properties.PaymentProperties;
import com.shop.common.exception.http.BadRequestException;
import com.shop.common.utils.ApiUtils;
import com.shop.order.service.OrderService;
import com.shop.payment.controller.request.PaymentCancelReqDto;
import com.shop.payment.controller.request.PaymentConfirmReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentProperties paymentProperties;

	private final OrderService orderService;
	private final ApiUtils apiUtils;
	private static final String PAYMENT_SUFFIX = "/payments/confirm";
	private static final String CANCEL_SUFFIX = "/payments/{paymentKey}/cancel";
	// 명등키 헤더
	private static final String IDEMPOTENCY_KEY = "Idempotency-Key";

	@PostMapping("/payment/confirm")
	@PreventDuplicate
	public ResponseEntity<Void> conformPayment(@RequestBody PaymentConfirmReqDto reqDto) {
		orderService.checkOrder(reqDto.getOrderId(), reqDto.getAmount());
		HttpHeaders headers = tossPaymentsRequestHeader();

		ResponseEntity<String> response = apiUtils.apiCall(paymentProperties.getUrl() + PAYMENT_SUFFIX,
			String.class, headers, reqDto, HttpMethod.POST);

		if (response.getStatusCode().is2xxSuccessful()) {
			orderService.paymentComplete(reqDto.getOrderId(), reqDto.getPaymentKey());
			return ResponseEntity.ok().build();
		}
		throw new BadRequestException("결제 실패");
	}



	@PostMapping("/payment/cancel")
	@PreventDuplicate
	public ResponseEntity<Void> cancelPayment(@RequestBody PaymentCancelReqDto reqDto){
		String url = (paymentProperties.getUrl() + CANCEL_SUFFIX)
			.replace("{paymentKey}", reqDto.getPaymentKey());
		HttpHeaders headers = tossPaymentsRequestHeader();
		// 명등키 헤더 추가
		headers.set(IDEMPOTENCY_KEY, reqDto.getOrderId().toString());

		ResponseEntity<String> response = apiUtils.apiCall(url, String.class, headers,
			reqDto, HttpMethod.POST);
		if (response.getStatusCode().is2xxSuccessful()) {
			orderService.paymentCancel(reqDto.getOrderId());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		throw new BadRequestException("결제 취소 실패");
	}


	private HttpHeaders tossPaymentsRequestHeader() {
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
