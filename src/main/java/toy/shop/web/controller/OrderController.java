package toy.shop.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toy.shop.oauth.dto.SessionUser;
import toy.shop.service.OrderService;
import toy.shop.web.argumentresolver.Login;
import toy.shop.web.dtorequest.OrderRequestDto;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public String order(OrderRequestDto orderRequestDto, @Login SessionUser sessionUser) {
        System.out.println("orderRequestDto = " + orderRequestDto);

        Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), sessionUser.getId());

        return "redirect:/";
    }

    @PatchMapping("/order/{orderId}")
    public Long orderCancel(@PathVariable Long orderId){
        Long cancelId = orderService.cancel(orderId);
        return cancelId;
    }


}
