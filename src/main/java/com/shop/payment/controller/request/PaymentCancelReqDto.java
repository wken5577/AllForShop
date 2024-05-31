package com.shop.payment.controller.request;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PaymentCancelReqDto {
	@NotBlank(message = "결제키는 필수입니다.")
	private String paymentKey;
	@NotNull(message = "주문번호는 필수입니다.")
	private UUID orderId;
	@NotBlank(message = "취소 사유는 필수입니다.")
	private String cancelReason;
}
