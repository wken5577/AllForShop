package com.shop.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.item.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {

	Optional<Category> findByCategoryName(String categoryName);
}
