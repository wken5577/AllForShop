package com.shop.order.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class BasketOrderRequestDto {

    private List<OrderRequestDto> itemArr;
    private String deliveryAddress;
    private long totalPrice;
    private String imp_uid;

}
