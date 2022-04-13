package toy.shop.web.dto.dtoresponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shop.entity.Category;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private String name;


    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
