package com.shop.basket.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddBasketItemReqDto {
	@NotNull
	private Long itemId;

	@Positive(message = "수량은 1 이상이어야 합니다.")
	private int quantity;
}
