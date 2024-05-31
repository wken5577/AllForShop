package com.shop.payment.controller.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmReqDto {

	@NotBlank(message = "주문번호는 필수입니다.")
	private UUID orderId;

	@Positive(message = "결제금액은 0보다 커야합니다.")
	private int amount;

	@NotBlank(message = "결제키는 필수입니다.")
	private String paymentKey;
}
