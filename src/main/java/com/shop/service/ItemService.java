package com.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shop.entity.Category;
import com.shop.entity.Item;
import com.shop.entity.ItemImages;
import com.shop.entity.User;
import com.shop.repository.CategoryRepository;
import com.shop.repository.item.ItemRepository;
import com.shop.repository.UserRepository;
import com.shop.web.dto.dtoresponse.BasketItemDto;
import com.shop.web.dto.dtoresponse.ItemResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public Long addItem(String username, Long categoryId, String name, int price, List<ItemImages> itemImages, String itemInfo) {
        User findUser = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("회원 정보가 없습니다."));

        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalStateException("카테고리 정보가 없습니다.")
        );

        Item item = new Item(findCategory, name, price, findUser, itemImages, itemInfo);
        itemRepository.save(item);

        return item.getId();
    }

    public Long deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        return itemId;
    }

    @Transactional(readOnly = true)
    public Page<ItemResponseDto> findItemsByCategory(Long categoryId, Pageable pageable) {
        Page<Item> findItems = itemRepository.findByCategoryId(categoryId, pageable);

        Page<ItemResponseDto> result = findItems.map(ItemResponseDto::new);

        return result;
    }

    @Transactional(readOnly = true)
    public Page<ItemResponseDto> findAll(Pageable pageable) {
        Page<Item> itemPage = itemRepository.findAll(pageable);
        Page<ItemResponseDto> items = itemPage.map(ItemResponseDto::new);

        return items;
    }

    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long itemId) {
        ItemResponseDto item = itemRepository.findById(itemId)
                .map(ItemResponseDto::new).get();

        return item;
    }

    @Transactional(readOnly = true)
    public long getItemPrice(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));
        return item.getPrice();
    }

    @Transactional(readOnly = true)
    public List<BasketItemDto> findByIds(ArrayList<Long> ids) {
        List<Item> findItems = itemRepository.findAllById(ids);
        List<BasketItemDto> result = findItems.stream().map(BasketItemDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
