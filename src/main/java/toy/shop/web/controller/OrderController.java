package toy.shop.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy.shop.service.ItemService;
import toy.shop.service.OrderService;
import toy.shop.web.dto.dtoresponse.ItemResponseDto;

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
