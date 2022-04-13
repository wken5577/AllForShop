package toy.shop.web.dto.dtoresponse.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;
import toy.shop.entity.Item;

@Getter
@Setter
@NoArgsConstructor
public class IndexItemResponseDto {

    private Long itemId;
    private String name;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Number price;
    private String storeFileName;


    public IndexItemResponseDto(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.storeFileName = item.getItemImages().get(0).getStoreFileName();
        this.itemId = item.getId();
    }

}
