package com.shop.web.dto.dtoresponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import com.shop.entity.Item;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponseDto {

    private Long itemId;
    private String name;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Number price;
    private String storeFileName;
    private String info;

    public ItemResponseDto(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.storeFileName = item.getItemImages().get(0).getStoreFileName();
        this.itemId = item.getId();
        this.info = item.getItemInfo();
    }

}
