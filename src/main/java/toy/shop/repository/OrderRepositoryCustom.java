package toy.shop.repository;


import toy.shop.web.dtoresponse.OrderResponseDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderResponseDto> findByUserId(Long userId);

}
