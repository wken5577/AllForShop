package toy.shop.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.shop.entity.Category;
import toy.shop.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query("select i from Item i join fetch i.itemImages where i.category.id = :categoryId")
    List<Item> findByCategoryId(@Param("categoryId") Long categoryId);

    @Override
    @EntityGraph(attributePaths = {"itemImages"})
    List<Item> findAll();

    @Query("select i from Item i where i.user.id = :userId")
    List<Item> findByUserId(@Param("userId") Long userId);


}
