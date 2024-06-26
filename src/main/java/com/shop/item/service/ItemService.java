package com.shop.item.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.common.exception.http.BadRequestException;
import com.shop.item.controller.response.ItemDetailRespDto;
import com.shop.item.controller.response.ItemListRespDto;
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

	public void addItem(Long userId, Long categoryId, String name, int price, List<ItemImages> itemImages,
		String itemInfo) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new BadRequestException("회원 정보가 없습니다."));

		Category category = categoryRepository.findById(categoryId).orElseThrow(
			() -> new BadRequestException("카테고리 정보가 없습니다.")
		);

		Item item = new Item(category, name, price, user, itemImages, itemInfo);
		itemRepository.save(item);
	}

	public void deleteItem(Long userId, Long itemId) {
		Item findItem = itemRepository.findById(itemId).orElseThrow(
			() -> new BadRequestException("상품 정보가 없습니다."));

		if (!findItem.getUser().getId().equals(userId)) {
			throw new BadRequestException("해당 상품을 삭제할 권한이 없습니다.");
		}

		itemRepository.delete(findItem);
	}

	@Transactional(readOnly = true)
	public ItemDetailRespDto getItemDetail(Long itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(
			() -> new BadRequestException("상품 정보가 없습니다."));
		return new ItemDetailRespDto(item);
	}

	@Transactional(readOnly = true)
	public ItemListRespDto getItems(String keyword, Long categoryId, Pageable pageable) {
		Page<Item> pages = itemRepository.findAll(keyword, categoryId, pageable);
		return new ItemListRespDto(pages);
	}
}
