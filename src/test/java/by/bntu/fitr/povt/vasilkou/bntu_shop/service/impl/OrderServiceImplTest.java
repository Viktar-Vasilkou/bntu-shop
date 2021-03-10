package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.OrderDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.*;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final List<Order> orderList = List.of(
            new Order(),
            new Order()
    );

    private static final List<OrderItem> orderItems = List.of(
            new OrderItem(),
            new OrderItem()
    );

    @Test
    public void testGetById_null_null() {
        assertNull(orderService.getById(null));
    }

    @Test
    public void testGetById_Zero_newOrder() {
        assertNotNull((orderService.getById(0L)));
    }

    @Test
    public void testGetById_MinusOne_newOrder() {
        assertNotNull((orderService.getById(-1L)));
    }

    @Test
    public void testGetById_tryToGetOrderWithId1_Order() {
        Long id = 1L;
        assertNull(orderService.getById(id).getId());
    }

    @Test
    public void testGetById_tryToGetOrderWithId1Repository_OrderWithId() {
        Long id = 1L;
        orderList.get(0).setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(orderList.get(0)));

        assertEquals(orderList.get(0), orderService.getById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    // ------------

    @Test
    public void testDelete_tryDeleteNull_false() {
        assertFalse(orderService.delete(null));
    }

    @Test
    public void testDelete_tryDeleteNewUser_false() {
        Order order = new Order();
        assertTrue(orderService.delete(order));
        verify(orderRepository, times(1)).delete(order);
    }

    // ------------

    @Test
    public void testGetAll_tryWithPageLessTheThenZero_empty() {
        int page = -1;
        boolean status = false;

        Page<Order> expected = Page.empty();

        assertEquals(expected, orderService.getAll(page, status));
    }

    @Test
    public void testGetAll_tryWithPositivePage_newPage() {
        int page = 0;
        boolean status = false;

        when(orderRepository.findAll()).thenReturn(orderList);

        assertEquals(new PageImpl<>(orderList, PageRequest.of(0, 10), orderList.size()),
                orderService.getAll(page, status));
    }

    @Test
    public void testGetAll_tryWithPositivePageWithStatusTrue_notEmpty() {
        int page = 0;
        boolean status = false;
        orderList.get(0).setStatus(status);
        orderList.get(1).setStatus(status);

        when(orderRepository.findAll()).thenReturn(orderList);

        assertEquals(new PageImpl<Order>(orderList, PageRequest.of(page, 10), orderList.size()),
                orderService.getAll(page, status));
    }

    @Test
    public void testSave_tryToSaveOrder_success() {
        Order expected = orderList.get(0);
        expected.setId(1L);

        when(orderRepository.save(new Order())).thenReturn(Order.builder().id(1L).build());

        Order actual = orderService.save(new Order());

        assertEquals(expected.getId(), actual.getId());

        verify(orderRepository, times(1)).save(new Order());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test //    Order createOrder(List<OrderItem> items, User user, OrderDto dto, BigDecimal totalPrice);
    public void testCreateOrder_tryToCreateNewOrder_newOrder() {
        Order expected = Order.builder()
                .orderItems(orderItems)
                .user(User.builder().build())
                .address("Address")
                .totalPrice(BigDecimal.TEN)
                .build();

        Order actual = orderService.createOrder(orderItems,
                User.builder().build(),
                OrderDto.builder().address("Address").build(),
                BigDecimal.TEN);

        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getOrderItems(), actual.getOrderItems());
        assertEquals(expected.getUser(), actual.getUser());
        assertEquals(expected.getTotalPrice(), actual.getTotalPrice());
    }

    @Test //    Order confirm(Order order);
    public void testConfirm_tryToConfirm_Confirm(){
        assertTrue(orderService.confirm(Order.builder().status(false).build()).isStatus());
    }

    @Test //    Page<Order> getAllByUser(int page, User user);
    public void testGetAllByUser_tryToGetAllByUser_allOrdersByUser(){
        Pageable paging = PageRequest.of(0, 15);
        User user = new User();

        when(orderRepository.findAllByUser(user, paging))
                .thenReturn(new PageImpl<>(orderList, paging, orderList.size()));

        Page<Order> actual = orderService.getAllByUser(0, user);

        assertEquals(orderList, actual.getContent());
        assertEquals(orderList.size(), actual.getTotalElements());
        assertEquals(0, actual.getNumber());

        verify(orderRepository, times(1)).findAllByUser(user, paging);
    }
}
