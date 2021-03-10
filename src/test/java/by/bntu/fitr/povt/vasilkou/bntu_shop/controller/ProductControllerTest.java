package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.CategoryRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.ProductRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CategoryService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProductControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProductsPage_tryToGetProductsPage_ProductsPage() throws Exception {
        PageRequest paging = PageRequest.of(0, 15);
        Page<Product> productPage = productRepository.findAllAvailable(paging);
        mockMvc.perform(get("/products"))
                .andExpect(model().attribute("currentPage", productPage.getNumber()))
                .andExpect(model().attribute("totalPages", productPage.getTotalPages()))
                .andExpect(model().attribute("totalElements", productPage.getTotalElements()))
                .andExpect(model().attribute("products", productPage.getContent()))
                .andExpect(model().attribute("categories", hasItem(allOf(
                        hasProperty("id", is(categoryRepository.findAll().get(0).getId())),
                        hasProperty("name", is(categoryRepository.findAll().get(0).getName()))
                ))))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductPage_tryToGetProductPage_ProductPage() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/products/" + id))
                .andExpect(model().attribute("product", productRepository.findById(id).get()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductPage_tryToGetUnavailableProductPage_ProductPage() throws Exception {
        Long id = 1002L;
        mockMvc.perform(get("/products/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }
}