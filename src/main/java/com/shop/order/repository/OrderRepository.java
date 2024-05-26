package com.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom{


}
