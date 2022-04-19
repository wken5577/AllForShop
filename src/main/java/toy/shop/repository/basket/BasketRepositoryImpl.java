package toy.shop.repository.basket;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Collection;
import static toy.shop.entity.QBasketItem.*;

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
