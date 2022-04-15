package toy.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence" )
public class Order {

    @Id @GeneratedValue(generator = "order_sequence")
    private Long id;

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

    private int totalPrice;

    private Order(User user,String deliveryAddress) {
        this.user = user;
        this.deliveryStatus = DeliveryStatus.PREPARING;
        this.orderStatus = OrderStatus.ORDER;
        this.deliveryAddress = deliveryAddress;
    }

    public static Order order(User user, String deliveryAddress, List<OrderItem> orderItems){
        Order order = new Order(user, deliveryAddress);
        order.setOrderItems(order, orderItems);

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

    public void orderCancel() {
        this.orderStatus = OrderStatus.CANCEL;
    }
}
