package com.shop.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.item.controller.request.CreateCategoryReqDto;
import com.shop.item.controller.response.CategoryListRespDto;
import com.shop.item.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	@PostMapping("/category")
	public ResponseEntity<Void> createCategory(@Validated @RequestBody CreateCategoryReqDto createCategoryReqDto) {
		categoryService.createCategory(createCategoryReqDto.getCategoryName());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/categories")
	public ResponseEntity<CategoryListRespDto> getCategories() {
		return ResponseEntity.ok(new CategoryListRespDto(categoryService.getCategories()));
	}
}
