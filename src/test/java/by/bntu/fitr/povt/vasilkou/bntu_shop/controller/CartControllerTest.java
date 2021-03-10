package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.OrderDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @Test
    public void testGetCart_tryToGetCart_Success() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(model().attribute("cart", new HashMap<Product, Integer>()))
                .andExpect(model().attribute("totalPrice", BigDecimal.ZERO))
                .andExpect(model().attribute("orderForm", new OrderDto()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/cart"));
    }

    @Test
    public void testAddProduct_tryToAddProductToCart_Success() throws Exception {
        mockMvc.perform(post("/cart/add")
                .param("id", "10")
                .param("amount", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    public void testDeleteProduct_tryToDeleteProductFromCart_Success() throws Exception {
        mockMvc.perform(delete("/cart/delete")
                .param("id", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }
}