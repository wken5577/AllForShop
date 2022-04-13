package toy.shop.web.dto.dtorequest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ItemCreateDto {

    private String itemName;
    private Long categoryId;
    private int price;
    private List<MultipartFile> file;
    private String itemInfo;

}
