package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.OrderItem;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.OrderItemRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private static final List<OrderItem> orderItems = List.of(
            OrderItem.builder().build(),
            OrderItem.builder().build()
    );

    private static final Map<Product, Integer> cart = new HashMap<>();

    static {
        cart.put( Product.builder().id(1L).build(), 2);
        cart.put( Product.builder().id(2L).build(), 1);
    }

    @Test
    public void testSave_tryToSaveUser_Success() {
        OrderItem expected = orderItems.get(0);
        expected.setId(1L);

        when(orderItemRepository.save(new OrderItem())).thenReturn(OrderItem.builder().id(1L).build());

        OrderItem actual = orderItemService.save(new OrderItem());

        assertEquals(expected.getId(), actual.getId());

        verify(orderItemRepository, times(1)).save(new OrderItem());
        verifyNoMoreInteractions(orderItemRepository);
    }

    @Test
    public void testDelete_tryToSaveAll_SavedAll() {
        List<OrderItem> list = List.of(new OrderItem(),new OrderItem());
        orderItems.get(0).setId(1L);
        orderItems.get(1).setId(2L);

        List<OrderItem> expected = orderItems;

        when(orderItemRepository.saveAll(list)).thenReturn(orderItems);

        List<OrderItem> actual = orderItemService.saveAll(list);

        assertEquals(expected, actual);

        verify(orderItemRepository, times(1)).saveAll(list);
        verifyNoMoreInteractions(orderItemRepository);
    }

    @Test
    public void testGetOrderItemsFromCart_tryToGetOrderItemsFromCart_orderItems() {
        List<OrderItem> expected = List.of(
                OrderItem.builder().product(Product.builder().id(1L).build()).amount(2).build(),
                OrderItem.builder().product(Product.builder().id(2L).build()).amount(1).build()
        );

        List<OrderItem> actual = orderItemService.getOrderItemsFromCart(cart);

        assertEquals(expected, actual);
    }
}