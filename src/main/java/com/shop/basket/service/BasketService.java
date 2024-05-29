package com.shop.basket.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.basket.entity.BasketItem;
import com.shop.basket.entity.ShopBasket;
import com.shop.item.entity.Item;
import com.shop.user.repository.UserRepository;
import com.shop.basket.repository.BasketRepository;
import com.shop.item.repository.ItemRepository;
import com.shop.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BasketService {

	private final BasketRepository basketRepository;
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;

	public Long addItem(Long itemId, String username) {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

		ShopBasket shopBasket = basketRepository.findByUserId(user.getId()).orElse(null);
		Item item = itemRepository.findById(itemId).
			orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));

		if (shopBasket != null) {
			List<BasketItem> basketItems = shopBasket.getBasketItems();
			for (BasketItem basketItem : basketItems) {
				if (basketItem.getItem().getId().equals(item.getId())) {
					return -1L;
				}
			}
			basketItems.add(new BasketItem(item, shopBasket));
			return shopBasket.getId();
		} else {
			ShopBasket newBasket = new ShopBasket(user, new BasketItem(item));
			basketRepository.save(newBasket);
			return newBasket.getId();
		}

	}

	public Long deleteBasketItem(List<Long> ids, String username) {

		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

		ShopBasket shopBasket = basketRepository.findByUserId(user.getId()).orElse(null);
		if (shopBasket != null) {
			return basketRepository.deleteItemsByIds(shopBasket.getId(), ids);
		}

		return -1L;
	}

	/**
	 * Create a basket for a new user
	 * @param newUser
	 *
	 * 처음 가입한 유저에 한에 1번만 호출
	 */
	public void createBasket(User newUser) {
		ShopBasket shopBasket = new ShopBasket(newUser);
		basketRepository.save(shopBasket);
	}
}
