package com.shop.basket.controller.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeleteBasketItemReqDto {
	List<Long> itemIds;
}
