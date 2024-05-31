package com.shop.order.controller.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderRespDto {
	private UUID orderId;
	private int amount;
}
