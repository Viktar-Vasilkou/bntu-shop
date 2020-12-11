package by.bntu.fitr.povt.vasilkou.bntu_shop.service.api;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.OrderItem;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;

import java.util.List;
import java.util.Map;

public interface OrderItemService {
    List<OrderItem> getOrderItemsFromCart(Map<Product, Integer> items);
    List<OrderItem> saveAll(List<OrderItem> items);
    OrderItem save(OrderItem item);
}
