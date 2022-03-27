package toy.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.shop.entity.Category;
import toy.shop.entity.Item;
import toy.shop.entity.ItemImages;
import toy.shop.entity.User;
import toy.shop.repository.CategoryRepository;
import toy.shop.repository.ItemRepository;
import toy.shop.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public Long addItem(Long userId, Long categoryId, String name, int price, int quantity, List<ItemImages> itemImages) {
        User findUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("회원 정보가 없습니다."));

        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalStateException("카테고리 정보가 없습니다.")
        );

        Item item = new Item(findCategory, name, price, quantity, findUser, itemImages);
        itemRepository.save(item);

        return item.getId();
    }

    public Long updateItem(Long categoryId, Long itemId, String name, int price, int quantity) {
        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new IllegalStateException("카테고리 정보가 없습니다.")
        );

        Item target = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalStateException("상품 정보가 없습니다."));

        target.update(findCategory,name,price,quantity);

        return target.getId();
    }


    public Long deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        return itemId;
    }
}
