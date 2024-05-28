package com.shop.item.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import com.shop.item.entity.Category;

import lombok.Getter;

@Getter
public class CategoryListRespDto {
	List<CategoryRespDto> categoryList;

	public CategoryListRespDto(List<Category> categoryList) {
		this.categoryList = categoryList.stream()
			.map(CategoryRespDto::new)
			.collect(Collectors.toList());
	}
}
