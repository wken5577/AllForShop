package toy.shop.web.dtorequest;

import lombok.Data;

@Data
public class ItemUpdateDto {

    private Long categoryId;
    private String name;
    private int price;
    private int quantity;


}
