package toy.shop.web.dtorequest;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemCreateDto {

    private String itemName;
    private Long categoryId;
    private int price;
    private int quantity;
    private List<MultipartFile> file;


}
