package com.shop.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shop.service.ItemService;
import com.shop.web.dto.dtoresponse.ItemResponseDto;
import com.shop.service.OrderService;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping("/order/{itemId}")
    public String orderPage(Model model, @PathVariable Long itemId) {
        ItemResponseDto item = itemService.findById(itemId);
        model.addAttribute("item", item);

        return "/order/orderForm";
    }

}
