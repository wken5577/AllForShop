package toy.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import toy.shop.entity.Category;
import toy.shop.repository.CategoryRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitDB {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    void init() {
        Category category1 = new Category("상의");
        Category category2 = new Category("아우터");
        Category category3 = new Category("하의");
        Category category4 = new Category("신발");

        categoryRepository.saveAll(List.of(category1,category2,category3,category4));
    }


}
