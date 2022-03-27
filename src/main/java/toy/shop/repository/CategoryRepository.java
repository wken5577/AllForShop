package toy.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
