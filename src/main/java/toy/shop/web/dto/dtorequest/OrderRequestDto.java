package toy.shop.web.dto.dtorequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderRequestDto {

    private int quantity;
    private Long itemId;
    private String deliveryAddress;

    public OrderRequestDto(int quantity, Long itemId, int price, String deliveryAddress) {
        this.quantity = quantity;
        this.itemId = itemId;
        this.deliveryAddress = deliveryAddress;
    }
}
