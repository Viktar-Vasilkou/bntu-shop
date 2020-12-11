package by.bntu.fitr.povt.vasilkou.bntu_shop.repositories;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Order;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByUser(User user, Pageable pageable);
}
