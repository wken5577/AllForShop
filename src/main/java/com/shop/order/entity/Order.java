package com.shop.order.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Persistable;

import com.github.f4b6a3.uuid.UuidCreator;
import com.shop.common.exception.entity.OrderAlreadyPaidException;
import com.shop.user.entity.User;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order implements Persistable<UUID> {

    @Id
    @Column(length = 16)
    @EqualsAndHashCode.Include
    private final UUID id = UuidCreator.getTimeOrdered();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String deliveryAddress;

    private String paymentKey;

    private int totalPrice;

    @Transient
    private boolean isNew = true;

    private Order(User user,String deliveryAddress) {
        this.user = user;
        this.deliveryStatus = DeliveryStatus.PREPARING;
        this.orderStatus = OrderStatus.AWAITING_PAYMENT_CONFIRMATION;
        this.deliveryAddress = deliveryAddress;
    }

    public static Order order(User user, String deliveryAddress, List<OrderItem> orderItems){
        Order order = new Order(user, deliveryAddress);
        setOrderItems(order, orderItems);

        return order;
    }

    private static void setOrderItems(Order order, List<OrderItem> orderItems) {
        int total = 0;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            total += orderItem.getTotalPrice();
        }
        order.totalPrice = total;
        order.orderItems = orderItems;
    }

    public void cancelOrder() throws OrderAlreadyPaidException {
        if (this.orderStatus == OrderStatus.PAYMENT_COMPLETE)
            throw new OrderAlreadyPaidException("이미 결제가 완료된 주문입니다. 결제 취소 후 다시시도해주세요.");
        this.orderStatus = OrderStatus.CANCEL;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    public void setPersisted() {
        this.isNew = false;
    }

    public void paymentComplete(String paymentKey) {
        this.orderStatus = OrderStatus.PAYMENT_COMPLETE;
        this.paymentKey = paymentKey;
    }

    public boolean isPaymentComplete() {
        return this.orderStatus == OrderStatus.PAYMENT_COMPLETE;
    }

    public void paymentCancel() {
        this.orderStatus = OrderStatus.PAYMENT_CANCEL;
    }
}
