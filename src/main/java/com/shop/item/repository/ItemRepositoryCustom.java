package com.shop.item.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shop.item.entity.Item;

public interface ItemRepositoryCustom {

	Page<Item> findAll(String keyword, Long categoryId, Pageable pageable);
}
