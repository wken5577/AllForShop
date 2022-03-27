package toy.shop.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.*;
import toy.shop.oauth.dto.SessionUser;
import toy.shop.repository.OrderRepository;
import toy.shop.web.dtorequest.OrderRequestDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;

    MockHttpSession session = new MockHttpSession();

    @AfterEach
    void clearSession() {
        session.clearAttributes();
    }

    @Test
    @Transactional
    @WithMockUser(username = "test")
    void orderCancelTest() throws Exception {
        // given
        User user = new User("test","test","test");
        em.persist(user);
        session.setAttribute("user",new SessionUser(user));


        Category category = new Category("아우터");
        em.persist(category);
        Item item1 = new Item(category,"가디건",70000,20, user, null);
        em.persist(item1);

        OrderItem orderItem1 =OrderItem.orderItem(item1,item1.getPrice(),1);

        // when
        Order order = Order.order(user, "수원시", List.of(orderItem1));
        em.persist(order);

        String url = "/api/order/" + order.getId();

        //then
        mvc.perform(patch(url).session(session))
                .andExpect(status().isOk());

        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(findOrder.getOrderItems().get(0).getItem().getQuantity()).isEqualTo(20);

    }


    @Test
    @Transactional
    @WithMockUser(username = "test")
    void orderTest() throws Exception {
        User user = new User("test","test","test");
        em.persist(user);

        SessionUser sessionUser = new SessionUser(user);
        session.setAttribute("user",sessionUser);

        Category category = new Category("아우터");
        em.persist(category);
        Item item1 = new Item(category,"가디건",70000,20, user, null);
        em.persist(item1);


        OrderRequestDto orderRequestDto = new OrderRequestDto(2, item1.getId(), 70000, "수원시");

        String jsonData = new ObjectMapper().writeValueAsString(orderRequestDto);

        MvcResult mvcResult = mvc.perform(
                        post("/api/order").session(session)
                                .content(jsonData)
                                .contentType(MediaType.APPLICATION_JSON)

                ).andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String result = response.getContentAsString();
        Long orderId = Long.valueOf(result);

        Order findOrder = orderRepository.findById(orderId).get();
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().get(0).getItem().getQuantity()).isEqualTo(18);
        assertThat(findOrder.getTotalPrice()).isEqualTo(140000);

    }












}