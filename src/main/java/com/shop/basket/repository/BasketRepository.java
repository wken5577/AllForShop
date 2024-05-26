package com.shop.basket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.basket.entity.ShopBasket;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<ShopBasket, Long>, BasketRepositoryCustom {

    Optional<ShopBasket> findByUserId(Long userId);
}
