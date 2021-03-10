package by.bntu.fitr.povt.vasilkou.bntu_shop.controller.admin;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.allOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-order-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-order-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getProducts_adminProductsPage_isOk() throws Exception {
        Pageable pageable = PageRequest.of(0, 15);
        Page<Product> productPage = productRepository.findAll(pageable);
        Product product = productPage.getContent().get(0);
        mockMvc.perform(get("/admin/products"))
                .andExpect(model().attribute("products", Matchers.hasItem(allOf(
                        Matchers.hasProperty("id", Matchers.is(product.getId())),
                        Matchers.hasProperty("name", Matchers.is(product.getName())),
                        Matchers.hasProperty("description", Matchers.is(product.getDescription())),
                        Matchers.hasProperty("cost", Matchers.is(product.getCost())),
                        Matchers.hasProperty("amount", Matchers.is(product.getAmount())),
                        Matchers.hasProperty("status", Matchers.is(product.isStatus()))
                ))))
                .andExpect(model().attribute("currentPage", productPage.getNumber()))
                .andExpect(model().attribute("totalPages", productPage.getTotalPages()))
                .andExpect(model().attribute("newProduct", new Product()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/products"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getProducts_adminProductsPage_accessDenied() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    public void getProducts_adminProductsPage_redirectToLogin() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void checkProduct_tryToGetProductEditPage_isOk() throws Exception{
        Long id = 1L;
        Product product = productRepository.findById(id).get();
        mockMvc.perform(get("/admin/products/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attribute("product", allOf(
                        Matchers.hasProperty("id", Matchers.is(product.getId())),
                        Matchers.hasProperty("name", Matchers.is(product.getName())),
                        Matchers.hasProperty("description", Matchers.is(product.getDescription())),
                        Matchers.hasProperty("cost", Matchers.is(product.getCost())),
                        Matchers.hasProperty("amount", Matchers.is(product.getAmount())),
                        Matchers.hasProperty("status", Matchers.is(product.isStatus()))
                )))
                .andExpect(view().name("admin/product-edit"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void checkProduct_tryToGetOrderPage_isOk() throws Exception{
        Long id = 10L;
        mockMvc.perform(get("/admin/products/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createProduct_tryToCreateNewProduct_newProduct() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test contract.jpg",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/admin/products/create")
                .file(file)
                .param("name", "Test")
                .param("description", "Test Description")
                .param("cost", "25")
                .param("amount", "10")
                .param("category", "1")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        assertTrue(productRepository.findAll().size() > 1);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createProduct_tryToCreateNewProduct_redirect() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test contract.jpg",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/admin/products/create")
                .file(file)
                .param("name", "")
                .param("description", "")
                .param("cost", "12")
                .param("amount", "10")
                .param("category", "1")
        )
                .andExpect(flash().attributeExists("newProduct"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        assertEquals(1, productRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateProduct_tryToUpdateProduct_successUpdate() throws Exception {
        String editName = "test-Name";
        Long id = 1L;
        mockMvc.perform(patch("/admin/products/" + id)
                .param("id", "1")
                .param("name", editName)
                .param("description", "Test Description")
                .param("cost", "25")
                .param("amount", "10")
                .param("category", "1")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        assertEquals(editName, productRepository.findById(1L).get().getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateProduct_tryToUpdateNewProduct_redirect() throws Exception {
        Long id = 1L;
        mockMvc.perform(patch("/admin/products/" + id)
                .param("id", "1")
                .param("name", "")
                .param("description", "")
                .param("cost", "12")
                .param("amount", "10")
                .param("category", "1")
        )
                .andExpect(flash().attribute("product", productRepository.findById(id).get()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products/" + id));

        assertNotEquals("", productRepository.findById(id).get().getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void activateProduct_setStatusTrue_true() throws Exception {
        Long id = 1L;
        mockMvc.perform(patch("/admin/products/" + id + "/activate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        assertTrue(productRepository.findById(id).get().isStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deactivateProduct() throws Exception {
        Long id = 1L;
        mockMvc.perform(patch("/admin/products/" + id + "/deactivate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        assertFalse(productRepository.findById(id).get().isStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteProduct() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/admin/products/" + id ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        assertFalse(productRepository.findById(id).isPresent());
    }
}