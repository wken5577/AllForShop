package toy.shop.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toy.shop.entity.Category;
import toy.shop.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @EntityGraph(attributePaths = {"itemImages"})
    List<Item> findByCategory(Category category);

    @Override
    @EntityGraph(attributePaths = {"itemImages"})
    List<Item> findAll();

}
