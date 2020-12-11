package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.OrderItem;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.OrderItemRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getOrderItemsFromCart(Map<Product, Integer> cart) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cart.entrySet()){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(entry.getKey());
            orderItem.setAmount(entry.getValue());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> saveAll(List<OrderItem> items) {
        return orderItemRepository.saveAll(items);
    }

    @Override
    public OrderItem save(OrderItem item) {
        return orderItemRepository.save(item);
    }
}
