package com.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.shop.service.CategoryService;
import com.shop.service.ItemService;
import com.shop.web.dto.dtoresponse.CategoryResponseDto;
import com.shop.web.dto.dtoresponse.ItemResponseDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CategoryService categoryService;
    private final ItemService itemService;

    @GetMapping("/")
    public String indexPage(Model model, @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable Pageable) {

        List<CategoryResponseDto> categories = categoryService.findAllDto();
        model.addAttribute("categories", categories);

        Page<ItemResponseDto> result = itemService.findAll(Pageable);
        model.addAttribute("items",result);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= result.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("numbers",pageNumbers);
        return "index";
    }

    @GetMapping("/category")
    public String getItemsByCategory(Long cgId, Model model,@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable Pageable) {

        List<CategoryResponseDto> categories = categoryService.findAllDto();
        model.addAttribute("categories", categories);

        Page<ItemResponseDto> result = itemService.findItemsByCategory(cgId, Pageable);
        model.addAttribute("items",result);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= result.getTotalPages(); i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("numbers",pageNumbers);
        model.addAttribute("categoryId",cgId);
        return "index";
    }



}
