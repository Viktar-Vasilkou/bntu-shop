package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Order;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.OrderRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-product-before.sql", "/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-product-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Test
    public void testGetOrders_tryToGetOrdersPageWithoutAuthentication_redirectToLogin() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails(value = "root", userDetailsServiceBeanName = "userDetailsServiceImpl")
    public void testGetOrders_tryToGetOrdersPageWithoutAuthentication_ordersPage() throws Exception {
        Pageable pageable = PageRequest.of(0, 15);
        Page<Order> orders = orderRepository.findAll(pageable);
        mockMvc.perform(get("/orders"))
                .andExpect(authenticated())
                .andExpect(model().attribute("orders", orders))
                .andExpect(view().name("user/orders"))
                .andExpect(status().isOk());
    }

    @Test
    public void testMakeOrder_tryToMakeOrderWithoutAuthentication_redirectToLogin() throws Exception {
        mockMvc.perform(post("/orders/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails(value = "root", userDetailsServiceBeanName = "userDetailsServiceImpl")
    public void testMakeOrder_tryToMakeOrderWithoutAuthentication_createOrder() throws Exception {
        String address = "Test Address";

        mockMvc.perform(post("/orders/create")
                .param("address", address))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        assertEquals(0, cartService.getCart().size());
        assertEquals(1, orderRepository.findAll().size());
    }
}