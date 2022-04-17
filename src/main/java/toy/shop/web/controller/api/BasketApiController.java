package toy.shop.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.shop.security.dto.PrincipalDetail;
import toy.shop.service.BasketService;

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


}
