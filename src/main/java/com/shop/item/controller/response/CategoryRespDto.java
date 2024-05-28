package com.shop.item.controller.response;

import com.shop.item.entity.Category;

import lombok.Getter;

@Getter
public class CategoryRespDto {
	private Long categoryId;
	private String categoryName;

	public CategoryRespDto(Category category) {
		this.categoryId = category.getId();
		this.categoryName = category.getCategoryName();
	}
}
