package com.shop.basket.controller;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.shop.security.dto.PrincipalDetail;
import com.shop.basket.service.BasketService;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class BasketApiController {

    private final BasketService basketService;

    @PostMapping("/basket")
    public ResponseEntity addShopBasket(@RequestBody String createBasketDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {

        if (principalDetail.getUser() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        JSONObject jsonObject = new JSONObject(createBasketDto);
        Object itemIdObj = jsonObject.get("itemId");
        Long itemId = Long.valueOf(itemIdObj.toString());

        Long result = basketService.addItem(itemId, principalDetail.getUsername());
        if (result.equals(-1L)){
            return new ResponseEntity("이미 저장된 상품입니다.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/basket")
    public ResponseEntity deleteBasketItem(@RequestBody String idsJsonArray, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        JSONObject jsonObject = new JSONObject(idsJsonArray);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        ArrayList<Long> ids = new ArrayList();

        for (Object o : jsonArray) {
            ids.add(Long.valueOf(o.toString()));
        }

        Long result = basketService.deleteBasketItem(ids, principalDetail.getUsername());
        if(result > 0){
            return ResponseEntity.ok(result);
        }

        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


}
