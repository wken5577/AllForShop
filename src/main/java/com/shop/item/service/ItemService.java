package com.shop.item.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.exception.http.BadRequestException;
import com.shop.item.entity.Category;
import com.shop.item.entity.Item;
import com.shop.item.entity.ItemImages;
import com.shop.item.repository.CategoryRepository;
import com.shop.user.repository.UserRepository;
import com.shop.item.repository.ItemRepository;
import com.shop.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

	private final ItemRepository itemRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;

	public Long addItem(String username, Long categoryId, String name, int price, List<ItemImages> itemImages,
		String itemInfo) {
		User findUser = userRepository.findByUsername(username).orElseThrow(
			() -> new BadRequestException("회원 정보가 없습니다."));

		Category findCategory = categoryRepository.findById(categoryId).orElseThrow(
			() -> new BadRequestException("카테고리 정보가 없습니다.")
		);

		Item item = new Item(findCategory, name, price, findUser, itemImages, itemInfo);
		itemRepository.save(item);

		return item.getId();
	}

	@Transactional(readOnly = true)
	public long getItemPrice(Long itemId) {
		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
		return item.getPrice();
	}

}
