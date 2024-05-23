package com.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.entity.Item;
import com.shop.entity.Order;

import com.shop.entity.OrderItem;
import com.shop.entity.User;
import com.shop.repository.item.ItemRepository;
import com.shop.repository.order.OrderRepository;
import com.shop.repository.UserRepository;
import com.shop.web.dto.dtorequest.OrderRequestDto;
import com.shop.web.dto.dtoresponse.OrderResponseDto;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    public Long orderOne(String deliveryAddress,Long itemId, int quantity, String username) {
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존제하지 않습니다. " + username));
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

    public List<OrderResponseDto> getOrders(Long userId) {
        return orderRepository.findByUserId(userId);

    }


    public Long order(List<OrderRequestDto> itemArr, String deliveryAddress, String username) {
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존제하지 않습니다. " + username));

        List<Long> itemIds = itemArr.stream().map(i -> i.getItemId()).collect(Collectors.toList());
        itemRepository.findAllById(itemIds);
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderRequestDto orderRequestDto : itemArr) {
            Item item = itemRepository.getById(orderRequestDto.getItemId());
            OrderItem orderItem = OrderItem.orderItem(item, item.getPrice(), orderRequestDto.getQuantity());
            orderItems.add(orderItem);
        }

        Order order = Order.order(findUser, deliveryAddress, orderItems);
        orderRepository.save(order);

        return order.getId();
    }
}
