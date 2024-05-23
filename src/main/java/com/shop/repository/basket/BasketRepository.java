package com.shop.repository.basket;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.entity.ShopBasket;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<ShopBasket, Long>, BasketRepositoryCustom {

    Optional<ShopBasket> findByUserId(Long userId);
}
