package com.shop.basket.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    public ShopBasket(User user, BasketItem basketItem) {
        this.user = user;
        this.basketItems.add(basketItem);
        basketItem.setShopBasket(this);
    }

    public ShopBasket(User user) {
        this.user = user;
    }

    public void addItemToBasket(Item item) {
        BasketItem basketItem = new BasketItem(item);
        this.basketItems.add(basketItem);
        basketItem.setShopBasket(this);
    }

}
