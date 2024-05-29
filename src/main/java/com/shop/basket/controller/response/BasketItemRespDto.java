package com.shop.basket.controller.response;

import com.shop.basket.entity.BasketItem;
import com.shop.basket.entity.ShopBasket;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketItemRespDto {
	private Long itemId;
	private int quantity;
	private String itemName;

	public BasketItemRespDto(BasketItem basketItem){
		this.itemId = basketItem.getItem().getId();
		this.quantity = basketItem.getQuantity();
		this.itemName = basketItem.getItem().getName();
	}
}
