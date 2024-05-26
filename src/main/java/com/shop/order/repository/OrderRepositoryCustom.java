package com.shop.order.repository;


import com.shop.order.repository.dto.OrderResponseDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderResponseDto> findByUserId(Long userId);

}
