package toy.shop.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toy.shop.service.OrderService;
import toy.shop.web.dto.dtorequest.OrderRequestDto;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public String order(OrderRequestDto orderRequestDto) {
        System.out.println("orderRequestDto = " + orderRequestDto);

        Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), null);

        return "redirect:/";
    }

}
