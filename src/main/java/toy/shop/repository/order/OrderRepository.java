package toy.shop.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom{


}
