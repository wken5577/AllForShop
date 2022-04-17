package toy.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.BasketItem;
import toy.shop.entity.Item;
import toy.shop.entity.ShopBasket;
import toy.shop.entity.User;
import toy.shop.repository.BasketRepository;
import toy.shop.repository.UserRepository;
import toy.shop.repository.item.ItemRepository;
import toy.shop.web.dto.dtoresponse.BasketItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Long addItem(Long itemId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        ShopBasket shopBasket = basketRepository.findByUserId(user.getId()).orElse(null);
        Item item = itemRepository.findById(itemId).
                orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다."));

        if (shopBasket != null) {
            List<BasketItem> basketItems = shopBasket.getBasketItems();
            for (BasketItem basketItem : basketItems) {
                if (basketItem.getItem().getId().equals(item.getId())) {
                    return -1L;
                }
            }
            basketItems.add(new BasketItem(item,shopBasket));
            return shopBasket.getId();
        }else{
            ShopBasket newBasket = new ShopBasket(user, new BasketItem(item));
            basketRepository.save(newBasket);
            return newBasket.getId();
        }

    }

    public List<BasketItemDto> getUserBasketItems(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));

        ShopBasket shopBasket = basketRepository.findByUserId(user.getId()).orElse(null);

        if (shopBasket != null) {
            List<BasketItem> basketItems = shopBasket.getBasketItems();
            List<BasketItemDto> result = basketItems.stream().map(b -> new BasketItemDto(b.getItem())).collect(Collectors.toList());
            return result;
        }

        return new ArrayList<>();
    }
}
