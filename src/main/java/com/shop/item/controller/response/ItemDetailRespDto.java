package com.shop.item.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import com.shop.item.entity.Item;
import com.shop.item.entity.ItemImages;

import lombok.Getter;

@Getter
public class ItemDetailRespDto {
	private Long categoryId;
	private String categoryName;
	private String itemName;
	private int price;
	private String itemInfo;
	private String ownerUserName;
	private List<String> images;

	public ItemDetailRespDto(Item item){
		this.categoryId = item.getCategory().getId();
		this.categoryName = item.getCategory().getCategoryName();
		this.itemName = item.getName();
		this.price = item.getPrice();
		this.itemInfo = item.getItemInfo();
		this.ownerUserName = item.getUser().getUsername();
		this.images = item.getItemImages().stream()
			.map(ItemImages::getStoreFileName)
			.collect(Collectors.toList());
	}
}
