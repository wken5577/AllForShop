package toy.shop.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toy.shop.service.OrderService;
import toy.shop.web.dto.dtorequest.OrderRequestDto;
import toy.shop.web.dto.dtoresponse.OrderResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/order/new")
    public Long order(OrderRequestDto orderRequestDto) {
        System.out.println("orderRequestDto = " + orderRequestDto);

        Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), null);

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
