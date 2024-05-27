package com.shop.basket.repository;

import static com.shop.basket.entity.QBasketItem.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Collection;

public class BasketRepositoryImpl implements BasketRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BasketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long deleteItemsByIds(Long shopBasketId, Collection<Long> ids) {

        long result = queryFactory
                .delete(basketItem)
                .where(basketItem.shopBasket.id.eq(shopBasketId).and(basketItem.item.id.in(ids)))
                .execute();

        return result;
    }

}
