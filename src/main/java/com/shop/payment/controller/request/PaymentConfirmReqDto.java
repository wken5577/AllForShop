package com.shop.payment.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentConfirmReqDto {

	@NotBlank(message = "주문번호는 필수입니다.")
	private String orderId;

	@NotBlank(message = "결제금액은 필수입니다.")
	private String amount;

	@NotBlank(message = "결제키는 필수입니다.")
	private String paymentKey;
}
