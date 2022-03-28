package toy.shop.web.dtorequest;

import lombok.Data;


@Data
public class OrderRequestDto {

    private int quantity;
    private Long itemId;
    private int price;
    private String deliveryAddress;

    public OrderRequestDto(int quantity, Long itemId, int price, String deliveryAddress) {
        this.quantity = quantity;
        this.itemId = itemId;
        this.price = price;
        this.deliveryAddress = deliveryAddress;
    }
}
