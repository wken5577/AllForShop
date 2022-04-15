package toy.shop.repository.order;


import toy.shop.web.dto.dtoresponse.OrderResponseDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderResponseDto> findByUserId(Long userId);

}
