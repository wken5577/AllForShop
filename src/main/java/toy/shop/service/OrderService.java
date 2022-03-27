package toy.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.shop.entity.Item;
import toy.shop.entity.Order;
import toy.shop.entity.OrderItem;
import toy.shop.entity.User;
import toy.shop.repository.ItemRepository;
import toy.shop.repository.OrderRepository;
import toy.shop.repository.UserRepository;
import toy.shop.web.dtorequest.OrderRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Long order(OrderRequestDto orderRequestDto, Long userId) {
        User findUser = userRepository.findById(userId).
                orElseThrow(() -> new IllegalStateException("회원 정보가 없습니다. 로그인이 필요합니다."));

        Long itemId = orderRequestDto.getItemId();
        Item findItem = itemRepository.findById(itemId).
                orElseThrow(() -> new IllegalStateException("상품 정보가 없습니다"));

        OrderItem orderItem = OrderItem.orderItem(findItem, orderRequestDto.getPrice(), orderRequestDto.getQuantity());

        Order order = Order.order(findUser, orderRequestDto.getDeliveryAddress(), List.of(orderItem));
        orderRepository.save(order);
        return order.getId();
    }


    public Long cancel(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).
                orElseThrow(() -> new IllegalStateException("주문 정보가 없습니다"));
        findOrder.orderCancel();
        return findOrder.getId();
    }
}
