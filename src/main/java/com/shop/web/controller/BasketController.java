package com.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.shop.security.dto.PrincipalDetail;
import com.shop.service.BasketService;
import com.shop.service.ItemService;
import com.shop.web.dto.dtoresponse.BasketItemDto;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final ItemService itemService;

    @GetMapping("/mybasket")
    public String getBasket(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        List<BasketItemDto> result = basketService.getUserBasketItems(principalDetail.getUser().getUsername());

        model.addAttribute("items", result);
        return "/basket/basketList";
    }



}
