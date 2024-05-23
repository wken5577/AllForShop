package com.shop.repository.order;


import com.shop.web.dto.dtoresponse.OrderResponseDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderResponseDto> findByUserId(Long userId);

}
