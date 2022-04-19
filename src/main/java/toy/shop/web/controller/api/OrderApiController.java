package toy.shop.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import toy.shop.repository.item.ItemRepository;
import toy.shop.security.dto.PrincipalDetail;
import toy.shop.service.BasketService;
import toy.shop.service.ItemService;
import toy.shop.service.OrderService;
import toy.shop.web.dto.dtorequest.BasketOrderRequestDto;
import toy.shop.web.dto.dtorequest.OrderRequestDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final BasketService basketService;

    @PostMapping("/api/order")
    public ResponseEntity order(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), principalDetail.getUser().getUsername());

        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/api/order/payment")
    public ResponseEntity orderPayment(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal PrincipalDetail principalDetail) throws  URISyntaxException {

        ResponseEntity<String> responseToken = requestToken();
        Object access_token = getAccessToken(responseToken);

        ResponseEntity<String> validationData =  getPaymentData(access_token, orderRequestDto.getImp_uid());

        long itemPrice = itemService.getItemPrice(orderRequestDto.getItemId());
        boolean validationPayment = validationPayment(validationData, itemPrice * orderRequestDto.getQuantity());

        if (validationPayment){
                    Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), principalDetail.getUser().getUsername());
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(1L);
    }

    @PostMapping("/basket/order")
    public ResponseEntity orderBasketItems(@RequestBody BasketOrderRequestDto basketOrderRequestDto,
                                           @AuthenticationPrincipal PrincipalDetail principalDetail) {

        String deliveryAddress = basketOrderRequestDto.getDeliveryAddress();
        List<OrderRequestDto> itemArr = basketOrderRequestDto.getItemArr();

        Long orderId = orderService.order(itemArr, deliveryAddress, principalDetail.getUsername());

        List<Long> itemIds = itemArr.stream().map(i -> i.getItemId()).collect(Collectors.toList());
        basketService.deleteBasketItem(itemIds, principalDetail.getUsername());

        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/basket/order/payment")
    public ResponseEntity orderBasketItemsPayment(@RequestBody BasketOrderRequestDto basketOrderRequestDto,
                                           @AuthenticationPrincipal PrincipalDetail principalDetail) throws URISyntaxException {

        ResponseEntity<String> responseToken = requestToken();
        Object access_token = getAccessToken(responseToken);

        ResponseEntity<String> paymentData = getPaymentData(access_token, basketOrderRequestDto.getImp_uid());
        long orderPrice  = basketOrderRequestDto.getTotalPrice();

        boolean validationPayment = validationPayment(paymentData, orderPrice);

        if(validationPayment){
            String deliveryAddress = basketOrderRequestDto.getDeliveryAddress();
            List<OrderRequestDto> itemArr = basketOrderRequestDto.getItemArr();

            Long orderId = orderService.order(itemArr, deliveryAddress, principalDetail.getUsername());

            List<Long> itemIds = itemArr.stream().map(i -> i.getItemId()).collect(Collectors.toList());
            basketService.deleteBasketItem(itemIds, principalDetail.getUsername());

            return ResponseEntity.ok(orderId);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private Object getAccessToken(ResponseEntity<String> responseToken) {
        String responseBody = responseToken.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject response = (JSONObject) jsonObject.get("response");
        Object access_token = response.get("access_token");
        return access_token;
    }

    private boolean validationPayment(ResponseEntity<String> validationData, long orderPrice) {
        JSONObject jsonObject = new JSONObject(validationData.getBody());
        JSONObject response = (JSONObject) jsonObject.get("response");
        Object amountObject = response.get("amount");
        long amount = Long.valueOf( amountObject.toString());

        if(orderPrice == amount) return true;

        return false;
    }

    private ResponseEntity<String> getPaymentData(Object access_token, String imp_uid) {
        String uri = "https://api.iamport.kr/payments/" + imp_uid;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",(String) access_token);

        HttpEntity request = new HttpEntity<>( headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                uri,
                HttpMethod.GET,
                request,
                String.class
        );

        return response;
    }


    private  ResponseEntity<String> requestToken() throws URISyntaxException {
        String reqUrl = "https://api.iamport.kr/users/getToken";
        URI uri = new URI(reqUrl);

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("imp_key", "8265177692437492");
        body.put("imp_secret", "a59d083c0ab847d6368d0e3ce3d56402cc7daf4a6aa11fba0a0ba3db2926130ab3e031386232279b");

        HttpEntity<String> tokenRequest = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> response = rt.exchange(
                uri,
                HttpMethod.POST,
                tokenRequest,
                String.class
        );

        return response;
    }


}
