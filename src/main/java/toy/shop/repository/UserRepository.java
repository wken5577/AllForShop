package toy.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.shop.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
