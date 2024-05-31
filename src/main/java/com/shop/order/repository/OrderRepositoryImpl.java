package com.shop.order.repository;

import static com.shop.item.entity.QItem.*;
import static com.shop.order.entity.QOrder.*;
import static com.shop.order.entity.QOrderItem.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.order.repository.dto.OrderDto;
import com.shop.order.repository.dto.OrderItemsDto;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public OrderRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<OrderDto> findByUserId(Long userId) {
		List<OrderDto> orders = queryFactory
			.select(
				Projections.constructor(OrderDto.class, order.id, order.deliveryStatus,
					order.orderStatus, order.totalPrice)
			)
			.from(order)
			.where(order.user.id.eq(userId))
			.fetch();

		List<UUID> orderIds = orders.stream().map(OrderDto::getOrderId).collect(Collectors.toList());

		List<OrderItemsDto> orderItems = getOrderItemsDtos(orderIds);
		Map<UUID, List<OrderItemsDto>> orderItemMap = getOrderItemMap(orderItems);

		orders.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));

		return orders;
	}

	private Map<UUID, List<OrderItemsDto>> getOrderItemMap(List<OrderItemsDto> orderItems) {
		return orderItems.stream().
			collect(Collectors.groupingBy(OrderItemsDto::getOrderId));
	}

	private List<OrderItemsDto> getOrderItemsDtos(List<UUID> orderIds) {
		List<OrderItemsDto> orderItems = queryFactory
			.select(
				Projections.constructor(OrderItemsDto.class, orderItem.order.id,
					item.name, item.price, orderItem.quantity,
					orderItem.totalPrice)
			)
			.from(orderItem)
			.join(orderItem.item, item)
			.where(orderItem.order.id.in(orderIds))
			.fetch();

		return orderItems;
	}
}
