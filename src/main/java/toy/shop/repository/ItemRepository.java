package toy.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.shop.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Page<Item> findByCategoryId( Long categoryId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"itemImages"})
    List<Item> findAll();

    @Query("select i from Item i where i.user.id = :userId")
    List<Item> findByUserId(@Param("userId") Long userId);


}
