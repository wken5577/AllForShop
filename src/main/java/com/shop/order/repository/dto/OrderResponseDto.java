package com.shop.order.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.shop.order.entity.DeliveryStatus;
import com.shop.order.entity.OrderStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {

    private UUID orderId;
    private DeliveryStatus deliveryStatus;
    private OrderStatus orderStatus;
    private int totalPrice;
    private List<OrderItemsDto> orderItems;

    public OrderResponseDto(UUID orderId, DeliveryStatus deliveryStatus, OrderStatus orderStatus, int totalPrice) {
        this.orderId = orderId;
        this.deliveryStatus = deliveryStatus;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }
}
