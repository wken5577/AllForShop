package toy.shop.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import toy.shop.web.dtoresponse.OrderItemsDto;
import toy.shop.web.dtoresponse.OrderResponseDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static toy.shop.entity.QItem.*;
import static toy.shop.entity.QOrder.*;
import static toy.shop.entity.QOrderItem.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderResponseDto> findByUserId(Long userId) {
        List<OrderResponseDto> orders = queryFactory
                .select(
                        Projections.constructor(OrderResponseDto.class, order.id, order.deliveryStatus,
                                order.orderStatus, order.totalPrice)
                )
                .from(order)
                .where(order.user.id.eq(userId))
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
                        Projections.constructor(OrderItemsDto.class, orderItem.order.id,
                                item.name, orderItem.price, orderItem.quantity, orderItem.totalPrice)
                )
                .from(orderItem)
                .join(orderItem.item, item)
                .where(orderItem.order.id.in(orderIds))
                .fetch();

        return orderItems;
    }


}
