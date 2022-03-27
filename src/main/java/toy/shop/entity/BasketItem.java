package toy.shop.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(name = "basket_item_sequence", sequenceName = "basket_item_sequence")
public class BasketItem {

    @Id @GeneratedValue(generator = "basket_item_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_basket_id")
    private ShopBasket shopBasket;

    private int quantity;
    private int price;

}
