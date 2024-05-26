package com.shop.basket.repository;

import java.util.Collection;

public interface BasketRepositoryCustom {

    Long deleteItemsByIds(Long shopBasketId, Collection<Long> ids);

}
