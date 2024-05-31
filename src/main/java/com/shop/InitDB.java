package com.shop;


import lombok.RequiredArgsConstructor;

import com.shop.item.entity.Category;
import com.shop.item.entity.Item;
import com.shop.item.entity.ItemImages;
import com.shop.item.repository.CategoryRepository;
import com.shop.item.repository.ItemRepository;
import com.shop.user.entity.User;
import com.shop.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitDB {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @PostConstruct
     void init() {
        Category category1 = new Category("TOP");
        Category category2 = new Category("OUTER");
        Category category3 = new Category("KNIT");
        Category category4 = new Category("SHIRTS");
        Category category5 = new Category("PANTS");
        Category category6 = new Category("SHOES");

        categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5, category6));

        User user = User.createNormalUser("user1", "1234", "email1");
        userRepository.save(user);

        Item item1 = new Item(category1, "테스트상품1", 100, user, new ArrayList<>(), "테스트상품1입니다.");
        Item item2 = new Item(category2, "테스트상품2", 100, user, new ArrayList<>(), "테스트상품2입니다.");
        Item item3 = new Item(category3, "테스트상품3", 100, user, new ArrayList<>(), "테스트상품3입니다.");
        Item item4 = new Item(category4, "테스트상품4", 100, user, new ArrayList<>(), "테스트상품4입니다.");

        itemRepository.saveAll(List.of(item1, item2, item3, item4));
    }


}
