package com.shop.order.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.order.controller.request.OrderReqDto;
import com.shop.order.controller.response.OrderListRespDto;
import com.shop.order.controller.response.OrderResultRespDto;
import com.shop.order.service.OrderService;
import com.shop.security.dto.PrincipalDetail;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<OrderResultRespDto> order(@RequestBody OrderReqDto orderRequestDto,
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail
	) {
		OrderResultRespDto res = orderService.createOrder(orderRequestDto, principalDetail.getUserId());
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/order/{orderId}")
	public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId,
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail
	) {
		orderService.cancelOrder(orderId, principalDetail.getUserId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/order")
	public ResponseEntity<OrderListRespDto> getOrders(
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail
	) {
		OrderListRespDto res = orderService.getOrders(principalDetail.getUserId());
		return ResponseEntity.ok(res);
	}
}
