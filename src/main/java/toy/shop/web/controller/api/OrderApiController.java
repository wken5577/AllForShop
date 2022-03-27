package toy.shop.web.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import toy.shop.oauth.dto.SessionUser;
import toy.shop.service.OrderService;
import toy.shop.web.dtorequest.OrderRequestDto;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public Long order(@RequestBody OrderRequestDto orderRequestDto, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        Long orderId = orderService.order(orderRequestDto, sessionUser.getId());

        return orderId;
    }

    @PatchMapping("/api/order/{orderId}")
    public Long orderCancel(@PathVariable Long orderId){
        Long cancelId = orderService.cancel(orderId);
        return cancelId;
    }


}
