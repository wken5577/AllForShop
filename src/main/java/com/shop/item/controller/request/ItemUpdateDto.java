package com.shop.item.controller.request;

import lombok.Data;

@Data
public class ItemUpdateDto {

    private Long categoryId;
    private String name;
    private int price;


}
