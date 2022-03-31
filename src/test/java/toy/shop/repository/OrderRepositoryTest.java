package toy.shop.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.*;
import toy.shop.repository.OrderRepository;
import toy.shop.web.dtoresponse.OrderResponseDto;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("주문기능 테스트")
    @Rollback(value = false)
    void orderTest1() throws Exception{
        // given
        User user = new User("test","test","test");
        em.persist(user);

        Category category = new Category("아우터");
        em.persist(category);
        Item item1 = new Item(category,"가디건",70000,20, user,null, null);
        em.persist(item1);

        OrderItem orderItem1 =OrderItem.orderItem(item1,item1.getPrice(),1);

        // when
        Order order = Order.order(user, "수원시", List.of(orderItem1));
        em.persist(order);

        // then
        Order findOrder = orderRepository.findById(order.getId()).get();
        List<OrderItem> orderItems = findOrder.getOrderItems();

        assertThat(findOrder.getTotalPrice()).isEqualTo(70000);
        assertThat(orderItems.size()).isEqualTo(1);
        assertThat(orderItems.get(0).getItem().getQuantity()).isEqualTo(19);

    }

    @Test
    @DisplayName("주문 재고수량 부족 테스트")
    @Rollback(value = false)
    void orderTest2() {
        //given
        User user = new User("test","test","test");
        em.persist(user);

        Category category = new Category("아우터");
        em.persist(category);
        Item item1 = new Item(category,"가디건",70000,20, user,null,null);
        em.persist(item1);

        //when then
        assertThrows(IllegalStateException.class, ()->
                OrderItem.orderItem(item1,item1.getPrice(),21));

    }

    @Test
    @DisplayName("주문 취소 테스트")
    void orderTest3() throws Exception {
        // given
        User user = new User("test","test","test");
        em.persist(user);

        Category category = new Category("아우터");
        em.persist(category);
        Item item1 = new Item(category,"가디건",70000,20, user,null,null);
        em.persist(item1);

        OrderItem orderItem1 =OrderItem.orderItem(item1,item1.getPrice(),1);

        // when
        Order order = Order.order(user, "수원시", List.of(orderItem1));
        em.persist(order);

        Order findOrder = orderRepository.findById(order.getId()).get();
        findOrder.orderCancel();
        //then

        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(findOrder.getOrderItems().get(0).getItem().getQuantity()).isEqualTo(20);


    }


}