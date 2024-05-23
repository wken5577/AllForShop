package com.shop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.repository.CategoryRepository;
import com.shop.web.dto.dtoresponse.CategoryResponseDto;

import lombok.RequiredArgsConstructor;

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
