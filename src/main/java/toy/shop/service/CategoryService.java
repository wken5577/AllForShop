package toy.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.shop.repository.CategoryRepository;
import toy.shop.web.dtoresponse.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> findAllDto() {
        List<CategoryResponseDto> result = categoryRepository.findAll()
                .stream().map(CategoryResponseDto::new).collect(Collectors.toList());
        return result;
    }

}
