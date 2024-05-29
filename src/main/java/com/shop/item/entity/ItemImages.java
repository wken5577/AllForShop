package com.shop.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@SequenceGenerator(name = "item_images_sequence", sequenceName = "item_images_sequence")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImages {

	@Id
	@GeneratedValue(generator = "item_images_sequence")
	@Column(name = "item_images_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	private String uploadFileName;

	private String storeFileName;

	public ItemImages(String uploadFileName, String storeFileName) {
		this.uploadFileName = uploadFileName;
		this.storeFileName = storeFileName;
	}

	protected void setItem(Item item) {
		this.item = item;
	}
}
