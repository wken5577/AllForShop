package com.shop.item.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.shop.common.entity.BaseEntity;
import com.shop.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence")
public class Item extends BaseEntity {

	@Id
	@GeneratedValue(generator = "item_sequence")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemImages> itemImages = new ArrayList<>();

	private String name;

	private String itemInfo;

	private int price;

	public Item(Category category, String name, int price, User user, List<ItemImages> itemImages, String itemInfo) {
		this.user = user;
		this.category = category;
		this.name = name;
		this.price = price;
		this.itemImages = itemImages;
		this.itemInfo = itemInfo;

		for (ItemImages itemImage : itemImages) {
			itemImage.setItem(this);
		}
	}
}
