package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.CategoryRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private static final Map<Product, Integer> cart = Map.of(
            Product.builder().id(1L).cost(BigDecimal.TEN).amount(10).build(), 1,
            Product.builder().id(2L).cost(BigDecimal.TEN).amount(10).build(), 2
    );

    @Test
    public void testGetCart() {
        assertNotNull(cartService.getCart());
        assertEquals(0, cartService.getCart().size());
    }

    @Test
    public void testAddCartItem_tryToAddItemToCart_AddedProduct() {
        Product product = Product.builder().id(4L).build();
        Integer amount = 1;

        cartService.addCartItem(product, amount);

        assertEquals(1, cartService.getCart().size());
        assertTrue(cartService.getCart().containsKey(product));
        assertEquals(Optional.of(1).get(), cartService.getCart().get(product));
    }

    @Test
    public void testAddCartItem_tryToAddItemToCart_UpdatedAmount() {
        Product product = Product.builder().id(2L).build();
        Integer amount = 1;

        cartService.addCartItem(product, amount);
        cartService.addCartItem(product, amount);

        assertEquals(1, cartService.getCart().size());
        assertTrue(cartService.getCart().containsKey(product));
        assertEquals(Optional.of(2).get(), cartService.getCart().get(product));
    }

    @Test
    public void testRemoveCartItem_tryToRemoveItem_removed() {
        Product product = Product.builder().id(2L).build();
        Integer amount = 1;

        cartService.addCartItem(product, amount);
        cartService.removeCartItem(product);

        assertEquals(0, cartService.getCart().size());
        assertFalse(cartService.getCart().containsKey(product));
    }

    @Test
    public void testGetTotalPrice_tryToGet30_30() {
        Product product = Product.builder().id(2L).cost(BigDecimal.TEN).build();
        Integer amount = 3;

        cartService.addCartItem(product, amount);

        BigDecimal expected = BigDecimal.valueOf(30);
        BigDecimal actual = cartService.getTotalPrice();

        assertEquals(expected, actual);
    }

    @Test
    public void testCheckout_tryToCheckout_Checkout() throws Exception {
        Product product = Product.builder().id(2L).cost(BigDecimal.TEN).amount(10).build();
        Integer amount = 3;

        cartService.addCartItem(product, amount);

        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product));

        ProductRepository spy = Mockito.spy(productRepository);

        cartService.checkout();

        assertEquals(0, cartService.getCart().size());
        verify(productRepository, times(1)).findById(2L);
    }

    @Test(expected = Exception.class)
    public void testCheckout_tryToCheckOut_ThrowException() throws Exception {
        Product product = Product.builder().id(2L).cost(BigDecimal.TEN).amount(5).build();
        Integer amount = 10;

        cartService.addCartItem(product, amount);

        when(productRepository.findById(2L))
                .thenReturn(Optional.of(Product.builder()
                        .id(1L)
                        .cost(BigDecimal.TEN)
                        .amount(5).build()));

        cartService.checkout();

        assertEquals(0, cartService.getCart().size());
    }
}