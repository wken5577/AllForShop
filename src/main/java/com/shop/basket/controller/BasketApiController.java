package com.shop.basket.controller;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.basket.controller.request.AddBasketItemReqDto;
import com.shop.basket.controller.request.DeleteBasketItemReqDto;
import com.shop.security.dto.PrincipalDetail;
import com.shop.basket.service.BasketService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class BasketApiController {

    private final BasketService basketService;

    @PostMapping("/basket/item")
    public ResponseEntity<Void> addShopBasket(@Validated @RequestBody AddBasketItemReqDto reqDto,
        @AuthenticationPrincipal PrincipalDetail principalDetail) {
        basketService.addItem(reqDto.getItemId(), reqDto.getQuantity(), principalDetail.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/basket/item")
    public ResponseEntity<Void> deleteBasketItem(@RequestBody DeleteBasketItemReqDto reqDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        basketService.deleteBasketItem(reqDto.getItemIds(), principalDetail.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
