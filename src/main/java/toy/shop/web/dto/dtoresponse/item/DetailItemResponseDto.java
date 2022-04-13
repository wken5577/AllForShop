package toy.shop.web.dto.dtoresponse.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import toy.shop.entity.Item;

@Getter
@Setter
@NoArgsConstructor
public class DetailItemResponseDto {

    private Long itemId;
    private String name;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Number price;

    private String info;
    private String storeFileName;

    public DetailItemResponseDto(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.info = item.getItemInfo();
        this.storeFileName = item.getItemImages().get(0).getStoreFileName();
    }



}
