package toy.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shop.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
