package com.shop.basket.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.shop.item.entity.Item;

@Entity
@SequenceGenerator(name = "basket_item_sequence", sequenceName = "basket_item_sequence")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BasketItem {

    @Id
    @GeneratedValue(generator = "basket_item_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_basket_id")
    private ShopBasket shopBasket;

    private int quantity;

    public BasketItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    protected void setShopBasket(ShopBasket shopBasket) {
        this.shopBasket = shopBasket;
    }

    public int getQuantity() {
        return quantity;
    }

}
