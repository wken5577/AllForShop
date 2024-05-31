package com.shop.order.controller;

import java.awt.image.VolatileImage;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.nodes.NodeId;

import com.shop.order.controller.request.OrderReqDto;
import com.shop.order.controller.response.OrderRespDto;
import com.shop.order.repository.dto.OrderResponseDto;
import com.shop.order.service.OrderService;
import com.shop.security.dto.PrincipalDetail;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<OrderRespDto> order(@RequestBody OrderReqDto orderRequestDto,
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail
	) {
		OrderRespDto res = orderService.createOrder(orderRequestDto, principalDetail.getUserId());
		return ResponseEntity.ok(res);
	}
}
