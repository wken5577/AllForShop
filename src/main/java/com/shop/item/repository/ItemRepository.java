package com.shop.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.item.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Page<Item> findByCategoryId( Long categoryId, Pageable pageable);

    List<Item> findByUserId(Long userId);

}
