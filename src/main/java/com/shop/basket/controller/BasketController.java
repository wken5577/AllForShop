package com.shop.basket.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.basket.controller.request.AddBasketItemReqDto;
import com.shop.basket.controller.response.BasketItemsRespDto;
import com.shop.basket.controller.request.DeleteBasketItemReqDto;
import com.shop.security.dto.PrincipalDetail;
import com.shop.basket.service.BasketService;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/basket/item")
    public ResponseEntity<Void> addShopBasket(@Validated @RequestBody AddBasketItemReqDto reqDto,
        @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail
    ) {
        basketService.addItem(reqDto.getItemId(), reqDto.getQuantity(), principalDetail.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/basket/item")
    public ResponseEntity<Void> deleteBasketItem(@RequestBody DeleteBasketItemReqDto reqDto,
        @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail
    ) {
        basketService.deleteBasketItem(reqDto.getItemIds(), principalDetail.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/basket/item")
    public ResponseEntity<BasketItemsRespDto> getBasketItems(
        @Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail
    ) {
        return ResponseEntity.ok(basketService.getBasketItems(principalDetail.getUserId()));
    }
}
