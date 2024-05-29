package com.shop.basket.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shop.common.exception.entity.DuplicatedBasketItemException;
import com.shop.item.entity.Item;
import com.shop.user.entity.User;

@Entity
@Getter
@SequenceGenerator(name = "shop_basket_sequence", sequenceName = "shop_basket_sequence")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopBasket {

    @Id
    @GeneratedValue(generator = "shop_basket_sequence")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shopBasket", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();

    public ShopBasket(User user) {
        this.user = user;
    }

    public void addItemToBasket(Item item, int quantity) throws DuplicatedBasketItemException {
        checkIfItemAlreadyInBasket(item);
        addBasketItem(new BasketItem(item, quantity));
    }

    private void addBasketItem(BasketItem basketItem) {
        this.basketItems.add(basketItem);
        basketItem.setShopBasket(this);
    }

    private void checkIfItemAlreadyInBasket(Item item) throws DuplicatedBasketItemException {
        Optional<BasketItem> duplicatedItem = this.basketItems.stream()
            .filter(basketItem -> basketItem.getItem().getId().equals(item.getId()))
            .findAny();
        if (duplicatedItem.isPresent()) {
            throw new DuplicatedBasketItemException("장바구니에 이미 존재하는 상품입니다.");
        }
    }

    public void deleteBasketItems(List<Long> itemIds) {
        for (Long itemId : itemIds) {
            Optional<BasketItem> targetBasketItem = this.basketItems.stream()
                .filter(item -> item.getItem().getId().equals(itemId))
                .findFirst();
            targetBasketItem.ifPresent(this::deleteBasketItem);
        }
    }

    private void deleteBasketItem(BasketItem basketItem) {
        this.basketItems.remove(basketItem);
        basketItem.setShopBasket(null);
    }
}
