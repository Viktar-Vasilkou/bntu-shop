package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Order;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
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

//    @Test
//    public void testGetById_null_null(){
//        assertNull(orderService.getById(null));
//    }
//
//    @Test
//    public void testGetById_Zero_newOrder(){
//        assertNotNull((orderService.getById(0L)));
//    }
//
//    @Test
//    public void testGetById_MinusOne_newOrder(){
//        assertNotNull((orderService.getById(-1L)));
//    }
//
//    @Test
//    public void testGetById_tryToGetOrderWithId1_Order(){
//        Long id = 1L;
//        assertNull(orderService.getById(id).getId());
//    }
//
//    @Test
//    public void testGetById_tryToGetOrderWithId1Repository_OrderWithId(){
//        Long id = 1L;
//        orderList.get(0).setId(id);
//
//        when(orderRepository.findById(id)).thenReturn(Optional.of(orderList.get(0)));
//
//        assertEquals(orderList.get(0), orderService.getById(id));
//        verify(orderRepository, times(1)).findById(id);
//    }

    // ------------

    @Test
    public void testDelete_tryDeleteNull_false(){
        assertFalse(orderService.delete(null));
    }

    @Test
    public void testDelete_tryDeleteNewUser_false(){
        Order order = new Order();
        assertTrue(orderService.delete(order));
        verify(orderRepository, times(1)).delete(order);
    }

    // ------------

//    @Test
//    public void testGetAll_tryWithPageLessTheThenZero_empty() {
//        int page = -1;
//        boolean status = false;
//
//        Page<Order> expected = Page.empty();
//
//        assertEquals(expected, orderService.getAll(page, status));
//    }
//
//    @Test
//    public void testGetAll_tryWithPositivePage_newPage() {
//        int page = 0;
//        boolean status = false;
//
//        when(orderRepository.findAll()).thenReturn(orderList);
//
//        assertEquals(new PageImpl<>(orderList, PageRequest.of(0, 10), orderList.size()),
//                orderService.getAll(page, status));
//    }
//
//    @Test
//    public void testGetAll_tryWithPositivePageWithStatusTrue_notEmpty() {
//        int page = 0;
//        boolean status = false;
//        orderList.get(0).setStatus(status);
//        orderList.get(1).setStatus(status);
//
//        when(orderRepository.findAll()).thenReturn(orderList);
//
//        assertEquals(new PageImpl<Order>(orderList, PageRequest.of(page, 10), orderList.size()),
//                orderService.getAll(page, status));
//    }
}
