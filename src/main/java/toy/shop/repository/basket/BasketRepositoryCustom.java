package toy.shop.repository.basket;

import toy.shop.entity.ShopBasket;

import java.util.Collection;
import java.util.Optional;

public interface BasketRepositoryCustom {

    Long deleteItemsByIds(Long shopBasketId, Collection<Long> ids);

}
