package toy.shop.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toy.shop.oauth.dto.SessionUser;
import toy.shop.service.OrderService;
import toy.shop.web.argumentresolver.Login;
import toy.shop.web.dtorequest.OrderRequestDto;
import toy.shop.web.dtoresponse.OrderResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/order/new")
    public Long order(OrderRequestDto orderRequestDto, @Login SessionUser sessionUser) {
        System.out.println("orderRequestDto = " + orderRequestDto);

        Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), sessionUser.getId());

        return orderId;
    }

    @PatchMapping("/api/order/{orderId}")
    public Long orderCancel(@PathVariable Long orderId){
        Long cancelId = orderService.cancel(orderId);
        return cancelId;
    }

    @GetMapping("/api/orders")
    public List<OrderResponseDto> getOrders(Long userId) {

        List<OrderResponseDto> orders = orderService.getOrders(userId);

        return orders;
    }



}
