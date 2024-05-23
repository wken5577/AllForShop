package com.shop.web.dto.dtoresponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.shop.entity.DeliveryStatus;
import com.shop.entity.OrderStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private DeliveryStatus deliveryStatus;
    private OrderStatus orderStatus;
    private int totalPrice;
    private List<OrderItemsDto> orderItems;

    public OrderResponseDto(Long orderId, DeliveryStatus deliveryStatus, OrderStatus orderStatus, int totalPrice) {
        this.orderId = orderId;
        this.deliveryStatus = deliveryStatus;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }
}
