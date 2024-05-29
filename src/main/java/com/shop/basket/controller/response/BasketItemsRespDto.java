package com.shop.basket.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import com.shop.basket.entity.ShopBasket;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketItemsRespDto {
	private List<BasketItemRespDto> items;

	public BasketItemsRespDto(ShopBasket shopBasket) {
		this.items = shopBasket.getBasketItems().stream()
			.map(BasketItemRespDto::new)
			.collect(Collectors.toList());
	}
}
