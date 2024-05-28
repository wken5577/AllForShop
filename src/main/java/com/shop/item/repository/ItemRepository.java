package com.shop.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}
