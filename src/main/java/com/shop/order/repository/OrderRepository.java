package com.shop.order.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID>, OrderRepositoryCustom{

	Optional<Order> findByIdAndUserId(UUID orderId, Long userId);
}
