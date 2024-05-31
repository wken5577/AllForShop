package com.shop.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.exception.entity.OrderAlreadyPaidException;
import com.shop.common.exception.http.BadRequestException;
import com.shop.item.entity.Item;
import com.shop.item.repository.ItemRepository;
import com.shop.order.controller.request.OrderReqDto;
import com.shop.order.controller.request.OrderReqItem;
import com.shop.order.controller.response.OrderListRespDto;
import com.shop.order.controller.response.OrderResultRespDto;
import com.shop.order.entity.Order;
import com.shop.order.entity.OrderItem;
import com.shop.order.repository.OrderRepository;
import com.shop.order.repository.dto.OrderDto;
import com.shop.user.entity.User;
import com.shop.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final UserRepository userRepository;
	private final ItemRepository itemRepository;
	private final OrderRepository orderRepository;

	public OrderResultRespDto createOrder(OrderReqDto orderRequestDto, Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BadRequestException("사용자를 찾을 수 없습니다."));
		List<OrderItem> orderItems = createOrderItems(orderRequestDto);
		Order order = Order.order(user, orderRequestDto.getDeliveryAddress(), orderItems);

		orderRepository.save(order);
		return new OrderResultRespDto(order.getId(), order.getTotalPrice());
	}

	private List<OrderItem> createOrderItems(OrderReqDto orderRequestDto) {
		List<OrderItem> orderItems = new ArrayList<>();

		List<Long> itemIds = orderRequestDto.getOrderReqItems()
			.stream().map(OrderReqItem::getItemId).toList();
		List<Item> items = itemRepository.findAllById(itemIds);

		items.stream().forEach(item -> {
			orderRequestDto.getOrderReqItems().stream()
				.filter(orderReqItem -> orderReqItem.getItemId().equals(item.getId()))
				.findFirst()
				.ifPresent(orderReqItem -> {
					orderItems.add(OrderItem.orderItem(item, orderReqItem.getQuantity()));
				});
		});

		return orderItems;
	}

	public void paymentComplete(UUID orderId, String paymentKey) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new BadRequestException("주문을 찾을 수 없습니다."));
		order.paymentComplete(paymentKey);
	}

	public void checkOrder(UUID orderId, int amount) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new BadRequestException("주문을 찾을 수 없습니다."));
		if (order.getTotalPrice() != amount)
			throw new BadRequestException("결제 금액이 일치하지 않습니다.");
		if (order.isPaymentComplete())
			throw new BadRequestException("이미 결제가 완료된 주문입니다.");
	}

	public void paymentCancel(UUID orderId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new BadRequestException("주문을 찾을 수 없습니다."));
		order.paymentCancel();
	}

	public void cancelOrder(UUID orderId, Long userId) {
		Order order = orderRepository.findByIdAndUserId(orderId, userId)
			.orElseThrow(() -> new BadRequestException("주문을 찾을 수 없습니다."));
		try {
			order.cancelOrder();
		} catch (OrderAlreadyPaidException e) {
			throw new BadRequestException(e);
		}
	}

	public OrderListRespDto getOrders(Long userId) {
		List<OrderDto> orders = orderRepository.findByUserId(userId);
		return new OrderListRespDto(orders);
	}
}
