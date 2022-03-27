package toy.shop.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(name = "shop_basket_sequence",sequenceName = "shop_basket_sequence")
public class ShopBasket {

    @Id @GeneratedValue(generator = "shop_basket_sequence")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shopBasket",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BasketItem> basketItems = new ArrayList<>();

    private int totalPrice;

}
