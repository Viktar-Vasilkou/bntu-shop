package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    private static final int AMOUNT_OF_PRODUCTS_ON_PAGE = 15;

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

    @Test //    Product save(Product product);
    public void testSave_tryToSaveProduct_Success(){
        Product expected = products.get(0);
        expected.setId(1L);

        when(productRepository.save(new Product())).thenReturn(Product.builder().id(1L).build());

        Product actual = productService.save(new Product());

        assertEquals(expected.getId(), actual.getId());

        verify(productRepository, times(1)).save(new Product());
        verifyNoMoreInteractions(productRepository);
    }

    @Test //    void delete(Product product);
    public void testDelete_tryToDeleteProduct_Success(){
        productService.delete(new Product());
        verify(productRepository, times(1)).delete(new Product());
        verifyNoMoreInteractions(productRepository);
    }

    @Test //    Product activate(Product product);
    public void testActivateProduct_Activate(){
        assertTrue(productService.activate(Product.builder().status(false).build()).isStatus());
    }

    @Test //    Product deactivate(Product product);
    public void testActivateProduct_Deactivate(){
        assertFalse(productService.deactivate(Product.builder().status(true).build()).isStatus());
    }

    @Test //    Page<Product> getAll(int page);
    public void testGetAll_tryToGetAllProducts_FirstPage_0_2(){
        int page = 0;
        Pageable paging = PageRequest.of(page, AMOUNT_OF_PRODUCTS_ON_PAGE);

        when(productRepository.findAll(paging)).thenReturn(new PageImpl<>(products, paging, products.size()));

        Page<Product> actual = productService.getAll(0);

        assertEquals(products, actual.getContent());
        assertEquals(products.size(), actual.getTotalElements());
        assertEquals(page, actual.getNumber());

        verify(productRepository, times(1)).findAll(paging);
    }

    @Test //    Page<Product> getAllAvailable(int page, Category category);
    public void testGetAllAvailable_tryToGetAllAvailableWithoutCategory_allAvailable(){
        int page = 0;
        Pageable paging = PageRequest.of(page, AMOUNT_OF_PRODUCTS_ON_PAGE);

        when(productRepository.findAllAvailable(paging)).thenReturn(new PageImpl<>(products, paging, products.size()));

        Page<Product> actual = productService.getAllAvailable(0, null);

        assertEquals(products, actual.getContent());
        assertEquals(products.size(), actual.getTotalElements());
        assertEquals(page, actual.getNumber());

        verify(productRepository, times(1)).findAllAvailable(paging);
    }

    @Test //    Page<Product> getAllAvailable(int page, Category category);
    public void testGetAllAvailable_tryToGetAllAvailableWithCategory_allAvailableWithCategory(){
        int page = 0;
        Pageable paging = PageRequest.of(page, AMOUNT_OF_PRODUCTS_ON_PAGE);
        Category category = new Category();

        when(productRepository.findAllByCategory(category, paging))
                .thenReturn(new PageImpl<>(products, paging, products.size()));

        Page<Product> actual = productService.getAllAvailable(0, category);

        assertEquals(products, actual.getContent());
        assertEquals(products.size(), actual.getTotalElements());
        assertEquals(page, actual.getNumber());

        verify(productRepository, times(1)).findAllByCategory(category, paging);
    }
}