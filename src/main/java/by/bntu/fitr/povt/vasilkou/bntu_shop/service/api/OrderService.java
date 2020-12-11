package by.bntu.fitr.povt.vasilkou.bntu_shop.service.api;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.OrderDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Order;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.OrderItem;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order getById(Long id);

    Order save(Order order);

    Order createOrder(List<OrderItem> items, User user, OrderDto dto, BigDecimal totalPrice);

    Order edit(Order order);

    boolean delete(Order order);

    Order confirm(Order order);

    Page<Order> getAllByUser(int page, User user);

    Page<Order> getAll(int page, boolean isActive);
}
