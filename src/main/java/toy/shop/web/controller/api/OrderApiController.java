package toy.shop.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import toy.shop.security.dto.PrincipalDetail;
import toy.shop.service.OrderService;
import toy.shop.web.dto.dtorequest.OrderRequestDto;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity order(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), principalDetail.getUser().getUsername());

        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/api/order/payment")
    public ResponseEntity orderPayment(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal PrincipalDetail principalDetail) throws  URISyntaxException {
        String reqUrl = "https://api.iamport.kr/users/getToken";
        URI uri = new URI(reqUrl);

        ResponseEntity<String> responseToken = requestToken(uri);

        String responseBody = responseToken.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject response = (JSONObject) jsonObject.get("response");
        Object access_token = response.get("access_token");

        ResponseEntity<String> validationData =  getPaymentData(access_token, orderRequestDto.getImp_uid());

        boolean validationPayment = validationPayment(validationData, orderRequestDto.getPaid_amount());

        if (validationPayment){
                    Long orderId = orderService.orderOne(orderRequestDto.getDeliveryAddress(),
                orderRequestDto.getItemId(),orderRequestDto.getQuantity(), principalDetail.getUser().getUsername());
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(1L);
    }

    private boolean validationPayment(ResponseEntity<String> validationData, long paid_amount) {
        JSONObject jsonObject = new JSONObject(validationData.getBody());
        JSONObject response = (JSONObject) jsonObject.get("response");
        Object amountObject = response.get("amount");
        long amount = Long.valueOf( amountObject.toString());

        if(paid_amount == amount) return true;

        return false;
    }

    private ResponseEntity<String> getPaymentData(Object access_token, String imp_uid) {
        String uri = "https://api.iamport.kr/payments/" + imp_uid;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",(String) access_token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>( headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                uri,
                HttpMethod.GET,
                request,
                String.class
        );

        return response;
    }


    private  ResponseEntity<String> requestToken(URI uri){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", "8265177692437492");
        params.add("imp_secret", "a59d083c0ab847d6368d0e3ce3d56402cc7daf4a6aa11fba0a0ba3db2926130ab3e031386232279b");

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                uri,
                HttpMethod.POST,
                tokenRequest,
                String.class
        );

        return response;
    }


}
