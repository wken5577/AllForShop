package toy.shop.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.web.dtoresponse.*;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void test() {
        List<CategoryResponseDto> result = categoryRepository.findAll()
                .stream().map(CategoryResponseDto::new).collect(Collectors.toList());

        Assertions.assertThat(result).isNotNull();
    }

}
