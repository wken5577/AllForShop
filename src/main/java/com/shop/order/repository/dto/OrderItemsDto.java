package com.shop.order.repository.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemsDto {

    private Long orderId;
    private String itemName;
    private int price;
    private int quantity;
    private int totalPrice;

    public OrderItemsDto(Long orderId, String itemName, int price, int quantity, int totalPrice) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
