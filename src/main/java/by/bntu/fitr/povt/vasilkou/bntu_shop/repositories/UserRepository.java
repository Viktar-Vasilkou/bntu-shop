package by.bntu.fitr.povt.vasilkou.bntu_shop.repositories;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(@Param("login") String login);
}
