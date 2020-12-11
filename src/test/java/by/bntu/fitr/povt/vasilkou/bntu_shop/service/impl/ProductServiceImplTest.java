package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.ProductRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private static final List<Product> products = List.of(
            new Product(),
            new Product()
    );

    @Test
    public void testGetById_tryToGetProductFromDB_product() {
        Long id = 1L;
        products.get(0).setId(id);

        Product expected = products.get(0);
        when(productRepository.findById(id)).thenReturn(Optional.of(products.get(0)));

        Product actual = productService.getById(id);

        assertEquals(expected, actual);

        verify(productRepository, times(1)).findById(id);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetById_tryToGetProductFromDB_newProduct() {
        Long id = 10L;

        Product expected = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(new Product()));

        Product actual = productService.getById(id);

        assertEquals(actual, expected);
        verify(productRepository, times(1)).findById(id);
        verifyNoMoreInteractions(productRepository);
    }
}