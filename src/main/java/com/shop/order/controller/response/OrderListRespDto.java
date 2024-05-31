package com.shop.order.controller.response;

import java.util.List;

import com.shop.order.repository.dto.OrderDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderListRespDto {
	private List<OrderDto> orders;
	public OrderListRespDto(List<OrderDto> orders) {
		this.orders = orders;
	}
}
