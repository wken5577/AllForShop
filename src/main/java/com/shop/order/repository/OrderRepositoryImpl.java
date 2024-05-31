package com.shop.order.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.item.entity.QItem;
import com.shop.order.entity.QOrder;
import com.shop.order.entity.QOrderItem;
import com.shop.order.repository.dto.OrderItemsDto;
import com.shop.order.repository.dto.OrderResponseDto;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public OrderRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<OrderResponseDto> findByUserId(Long userId) {
		List<OrderResponseDto> orders = queryFactory
			.select(
				Projections.constructor(OrderResponseDto.class, QOrder.order.id, QOrder.order.deliveryStatus,
					QOrder.order.orderStatus, QOrder.order.totalPrice)
			)
			.from(QOrder.order)
			.where(QOrder.order.user.id.eq(userId))
			.fetch();

		List<UUID> orderIds = orders.stream().map(o -> o.getOrderId()).collect(Collectors.toList());

		List<OrderItemsDto> orderItems = getOrderItemsDtos(orderIds);
		Map<Long, List<OrderItemsDto>> orderItemMap = getOrderItemMap(orderItems);

		orders.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));

		return orders;
	}

	private Map<Long, List<OrderItemsDto>> getOrderItemMap(List<OrderItemsDto> orderItems) {
		return orderItems.stream().
			collect(Collectors.groupingBy(orderItem -> orderItem.getOrderId()));
	}

	private List<OrderItemsDto> getOrderItemsDtos(List<UUID> orderIds) {
		List<OrderItemsDto> orderItems = queryFactory
			.select(
				Projections.constructor(OrderItemsDto.class, QOrderItem.orderItem.order.id,
					QItem.item.name, QItem.item.price, QOrderItem.orderItem.quantity,
					QOrderItem.orderItem.totalPrice)
			)
			.from(QOrderItem.orderItem)
			.join(QOrderItem.orderItem.item, QItem.item)
			.where(QOrderItem.orderItem.order.id.in(orderIds))
			.fetch();

		return orderItems;
	}
}
