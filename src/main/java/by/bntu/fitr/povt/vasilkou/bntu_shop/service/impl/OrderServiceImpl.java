package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.OrderDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Order;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.OrderItem;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.OrderRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getById(Long id) {
        if (id == null) {
            return null;
        }
        return orderRepository.findById(id).orElse(new Order());
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(List<OrderItem> items, User user, OrderDto dto, BigDecimal totalPrice) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(items);
        order.setAddress(dto.getAddress());
        order.setTotalPrice(totalPrice);
        order.setStatus(false);
        order.setDate(LocalDateTime.now());
        return order;
    }

    @Override
    public Order edit(Order order) {
        return null;
    }

    @Override
    public boolean delete(Order order) {
        if (order == null) {
            return false;
        }

        orderRepository.delete(order);
        return true;
    }

    @Override
    public Order confirm(Order order) {
        order.setStatus(true);
        return order;
    }

    @Override
    @Transactional
    public Page<Order> getAllByUser(int page, User user) {

        return orderRepository.findAllByUser(user, PageRequest.of(page, 15));
    }

    @Override
    public Page<Order> getAll(int page, boolean isActive) {
        if (page < 0) {
            return Page.empty();
        }

        int size = 10;

        List<Order> orderList = orderRepository.findAll();
        orderList = orderList.stream().filter(p -> p.isStatus() == isActive).collect(Collectors.toList());

        return new PageImpl<>(orderList, PageRequest.of(page, size), orderList.size());
    }
}
