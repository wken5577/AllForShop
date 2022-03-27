package toy.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "order_item_sequence", sequenceName = "order_item_sequence")
public class OrderItem {

    @Id @GeneratedValue(generator = "order_item_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int price;

    private int quantity;

    private OrderItem(Item item, int price, int quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    protected void setOrder(Order order){
        this.order = order;
    }

    public static OrderItem orderItem(Item item, int price, int quantity) {
        int restQuantity = item.getQuantity() - quantity;
        if (restQuantity < 0){
            throw new IllegalStateException("재고수량 부족");
        }
        item.minusQuantity(quantity);
        return new OrderItem(item, price, quantity);
    }


    public int getTotalPrice() {
        return price * quantity;
    }


}
