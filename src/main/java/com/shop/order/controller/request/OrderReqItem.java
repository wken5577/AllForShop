package com.shop.order.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderReqItem {

	@NotNull
	private Long itemId;

	@Positive
	private int quantity;
}
