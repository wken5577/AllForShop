package toy.shop.web.dtoresponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toy.shop.entity.Item;

@Getter
@Setter
@NoArgsConstructor
public class IndexItemResponseDto {

    String name;
    String price;
    String storeFileName;

    public IndexItemResponseDto(Item item) {
        this.name = item.getName();
        this.price = String.valueOf(item.getPrice());
        this.storeFileName = item.getItemImages().get(0).getStoreFileName();
    }
}
