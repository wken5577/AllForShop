package com.shop.repository.order;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.entity.QItem;
import com.shop.entity.QOrder;
import com.shop.entity.QOrderItem;
import com.shop.web.dto.dtoresponse.OrderItemsDto;
import com.shop.web.dto.dtoresponse.OrderResponseDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

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


        List<Long> orderIds = orders.stream().map(o -> o.getOrderId()).collect(Collectors.toList());

        List<OrderItemsDto> orderItems = getOrderItemsDtos(orderIds);
        Map<Long, List<OrderItemsDto>> orderItemMap = getOrderItemMap(orderItems);

        orders.forEach(order -> order.setOrderItems(orderItemMap.get(order.getOrderId())));

        return orders;
    }


    public Map<Long, List<OrderItemsDto>> getOrderItemMap(List<OrderItemsDto> orderItems) {
        return orderItems.stream().
                collect(Collectors.groupingBy(orderItem -> orderItem.getOrderId()));
    }


    public List<OrderItemsDto> getOrderItemsDtos(List<Long> orderIds) {
        List<OrderItemsDto> orderItems = queryFactory
                .select(
                        Projections.constructor(OrderItemsDto.class, QOrderItem.orderItem.order.id,
                                QItem.item.name, QOrderItem.orderItem.price, QOrderItem.orderItem.quantity, QOrderItem.orderItem.totalPrice)
                )
                .from(QOrderItem.orderItem)
                .join(QOrderItem.orderItem.item, QItem.item)
                .where(QOrderItem.orderItem.order.id.in(orderIds))
                .fetch();

        return orderItems;
    }
}
