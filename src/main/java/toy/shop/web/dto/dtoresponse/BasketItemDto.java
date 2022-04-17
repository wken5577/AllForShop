package toy.shop.web.dto.dtoresponse;

import lombok.Data;
import lombok.NoArgsConstructor;
import toy.shop.entity.Item;

@Data
@NoArgsConstructor
public class BasketItemDto {

    private Long itemId;
    private String itemName;
    private int price;

    public BasketItemDto(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.price = item.getPrice();
    }
}
