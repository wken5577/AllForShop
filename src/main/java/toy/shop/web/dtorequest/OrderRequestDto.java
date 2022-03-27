package toy.shop.web.dtorequest;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class OrderRequestDto {

    private int quantity;
    private Long itemId;
    private int price;
    private String deliveryAddress;

}
