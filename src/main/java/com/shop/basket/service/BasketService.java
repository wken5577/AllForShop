package com.shop.basket.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.basket.entity.ShopBasket;
import com.shop.common.exception.entity.DuplicatedBasketItemException;
import com.shop.common.exception.http.BadRequestException;
import com.shop.item.entity.Item;
import com.shop.user.repository.UserRepository;
import com.shop.basket.repository.BasketRepository;
import com.shop.item.repository.ItemRepository;
import com.shop.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BasketService {

	private final BasketRepository basketRepository;
	private final ItemRepository itemRepository;

	@Transactional
	public void addItem(Long itemId, int quantity, Long userId) {
		Item item = itemRepository.findById(itemId)
			.orElseThrow(() -> new BadRequestException("해당 상품이 존재하지 않습니다."));
		ShopBasket shopBasket = basketRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalStateException("해당 유저의 장바구니가 존재하지 않습니다."));

		try {
			shopBasket.addItemToBasket(item, quantity);
		} catch (DuplicatedBasketItemException e) {
			throw new BadRequestException(e);
		}
	}

	@Transactional
	public void deleteBasketItem(List<Long> itemIds, Long userId) {
	 	ShopBasket shopBasket = basketRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalStateException("해당 유저의 장바구니가 존재하지 않습니다."));
		 shopBasket.deleteBasketItems(itemIds);
	}

	/**
	 * Create a basket for a new user
	 * @param newUser
	 *
	 * 처음 가입한 유저에 한에 1번만 호출
	 */
	@Transactional
	public void createBasket(User newUser) {
		ShopBasket shopBasket = new ShopBasket(newUser);
		basketRepository.save(shopBasket);
	}


}
