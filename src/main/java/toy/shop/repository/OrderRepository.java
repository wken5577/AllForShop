package toy.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
