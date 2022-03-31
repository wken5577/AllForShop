package toy.shop.web.dtoresponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.shop.entity.Item;

@Getter
@Setter
@NoArgsConstructor
public class AdminItemResponseDto {

    private String name;
    private int quantity;
    private int price;

    public AdminItemResponseDto(Item item) {
        this.name = item.getName();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
    }
}
