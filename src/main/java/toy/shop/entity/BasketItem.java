package toy.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Getter
@SequenceGenerator(name = "basket_item_sequence", sequenceName = "basket_item_sequence")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public BasketItem(Item item) {
        this.item = item;
    }

    public BasketItem(Item item, ShopBasket shopBasket) {
        this.item = item;
        setShopBasket(shopBasket);
    }

    protected void setShopBasket(ShopBasket shopBasket) {
        this.shopBasket = shopBasket;
    }

}
