package by.bntu.fitr.povt.vasilkou.bntu_shop.repositories;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
