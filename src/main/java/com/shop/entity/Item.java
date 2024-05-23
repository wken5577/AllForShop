package com.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence")
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(generator = "item_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public void update(Category category,String name, int price){
        this.category = category;
        this.name = name;
        this.price = price;
    }
}
