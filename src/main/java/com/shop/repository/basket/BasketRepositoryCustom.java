package com.shop.repository.basket;

import java.util.Collection;

public interface BasketRepositoryCustom {

    Long deleteItemsByIds(Long shopBasketId, Collection<Long> ids);

}
