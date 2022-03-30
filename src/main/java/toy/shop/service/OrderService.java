package toy.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import toy.shop.entity.Item;
import toy.shop.entity.Order;

import toy.shop.entity.OrderItem;
import toy.shop.entity.User;
import toy.shop.repository.ItemRepository;
import toy.shop.repository.OrderRepository;
import toy.shop.repository.UserRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    public Long orderOne(String deliveryAddress,Long itemId, int quantity, Long userId) {
        User findUser = userRepository.getById(userId);
        Item findItem = itemRepository.getById(itemId);

        OrderItem orderItem = OrderItem.orderItem(findItem, findItem.getPrice(), quantity);

        Order order = Order.order(findUser, deliveryAddress, List.of(orderItem));
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
