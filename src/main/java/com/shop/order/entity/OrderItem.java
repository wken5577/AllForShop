package com.shop.order.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.shop.item.entity.Item;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "order_item_sequence", sequenceName = "order_item_sequence")
public class OrderItem {

    @Id @GeneratedValue(generator = "order_item_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int pricePerItem;

    private int quantity;

    @Getter
    private int totalPrice;

    private OrderItem(Item item, int pricePerItem, int quantity, int totalPrice) {
        this.item = item;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    protected void setOrder(Order order){
        this.order = order;
    }

    public static OrderItem orderItem(Item item, int price, int quantity) {
        return new OrderItem(item, price, quantity, price * quantity);
    }

}
