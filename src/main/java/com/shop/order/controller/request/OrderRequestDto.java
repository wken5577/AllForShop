package com.shop.order.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderRequestDto {

    private int quantity;
    private Long itemId;
    private String deliveryAddress;
    private String imp_uid;

}
