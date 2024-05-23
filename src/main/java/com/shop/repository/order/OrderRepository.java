package com.shop.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom{


}
