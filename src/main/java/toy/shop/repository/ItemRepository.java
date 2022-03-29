package toy.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toy.shop.entity.Category;
import toy.shop.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {


    List<Item> findByCategory(Category category);

}
