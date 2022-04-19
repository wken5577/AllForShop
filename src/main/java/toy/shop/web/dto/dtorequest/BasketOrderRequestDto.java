package toy.shop.web.dto.dtorequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class BasketOrderRequestDto {

    private List<OrderRequestDto> itemArr;
    private String deliveryAddress;
    private long totalPrice;
    private String imp_uid;

}
