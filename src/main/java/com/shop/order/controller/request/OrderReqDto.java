package com.shop.order.controller.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderReqDto {

	@NotEmpty(message = "주문 항목은 필수입니다.")
	private List<OrderReqItem> orderReqItems;

	@NotBlank(message = "배송지 주소는 필수입니다.")
	private String deliveryAddress;
}
