package com.shop.order.repository;


import com.shop.order.repository.dto.OrderDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderDto> findByUserId(Long userId);

}
