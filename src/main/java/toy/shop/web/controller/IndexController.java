package toy.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import toy.shop.oauth.dto.SessionUser;
import toy.shop.service.CategoryService;
import toy.shop.service.ItemService;
import toy.shop.web.argumentresolver.Login;
import toy.shop.web.dtoresponse.CategoryResponseDto;
import toy.shop.web.dtoresponse.IndexItemResponseDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CategoryService categoryService;
    private final ItemService itemService;

    @GetMapping("/")
    public String indexPage(@Login SessionUser sessionUser, Model model) {
        model.addAttribute("user", sessionUser);

        List<CategoryResponseDto> categories = categoryService.findAllDto();
        model.addAttribute("categories", categories);

        List<IndexItemResponseDto> result = itemService.findAll();
        model.addAttribute("items",result);

        return "index";
    }

    @GetMapping("/category")
    public String getItemsByCategory(Long cgId, Model model, @Login SessionUser sessionUser) {
        model.addAttribute("user", sessionUser);

        List<CategoryResponseDto> categories = categoryService.findAllDto();
        model.addAttribute("categories", categories);

        List<IndexItemResponseDto> result = itemService.findItemsByCategory(cgId);
        model.addAttribute("items",result);
        return "index";
    }



}
