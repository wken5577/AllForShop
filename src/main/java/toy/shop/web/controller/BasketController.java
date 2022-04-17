package toy.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toy.shop.security.dto.PrincipalDetail;
import toy.shop.service.BasketService;
import toy.shop.web.dto.dtoresponse.BasketItemDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/mybasket")
    public String getBasket(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        List<BasketItemDto> result = basketService.getUserBasketItems(principalDetail.getUser().getUsername());
        System.out.println("result = " + result);

        model.addAttribute("items", result);
        return "/basket/basketList";
    }


}
