package com.shop.item.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.shop.item.entity.Item;

import lombok.Getter;

@Getter
public class ItemListRespDto {
	private List<ItemDetailRespDto> itemList;
	private int totalPage;
	private int currentPage;

	public ItemListRespDto(Page<Item> itemList) {
		this.itemList = itemList.stream()
			.map(ItemDetailRespDto::new)
			.collect(Collectors.toList());
		this.totalPage = itemList.getTotalPages();
		this.currentPage = itemList.getNumber() + 1;
	}
}
