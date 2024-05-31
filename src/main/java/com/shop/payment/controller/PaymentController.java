package com.shop.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.shop.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;
	private final OrderService orderService;

	@PostMapping("/payment/confirm")
	@PreventDuplicate
	public ResponseEntity<Void> conformPayment(@RequestBody PaymentConfirmReqDto reqDto) {
		orderService.checkOrder(reqDto.getOrderId(), reqDto.getAmount());
		if (paymentService.confirmPayment(reqDto.getOrderId(), reqDto.getPaymentKey(), reqDto.getAmount())) {
			orderService.paymentComplete(reqDto.getOrderId(), reqDto.getPaymentKey());
			return ResponseEntity.ok().build();
		}
		throw new BadRequestException("결제 실패");
	}

	@PostMapping("/payment/cancel")
	@PreventDuplicate
	public ResponseEntity<Void> cancelPayment(@RequestBody PaymentCancelReqDto reqDto) {
		if (paymentService.cancelPayment(reqDto.getOrderId(), reqDto.getPaymentKey(), reqDto.getCancelReason())) {
			orderService.paymentCancel(reqDto.getOrderId());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		throw new BadRequestException("결제 취소 실패");
	}
}
