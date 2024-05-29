package com.shop.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.exception.http.BadRequestException;
import com.shop.item.entity.Category;
import com.shop.item.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public void createCategory(String categoryName) {
		categoryRepository.findByCategoryName(categoryName)
			.ifPresent(category -> {
				throw new BadRequestException("이미 존재하는 카테고리입니다.");
			});
		categoryRepository.save(new Category(categoryName));
	}

	public void deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
			.orElseThrow(() -> new BadRequestException("해당 카테고리가 존재하지 않습니다."));
		categoryRepository.delete(category);
	}

	@Transactional(readOnly = true)
	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}
}
