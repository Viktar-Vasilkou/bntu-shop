package by.bntu.fitr.povt.vasilkou.bntu_shop.controller.admin;

import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.OrderRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-order-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-order-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminOrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getCategories_TryToGetPage_isOk() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("orders", Matchers.hasItem(allOf(
                        Matchers.hasProperty("id", Matchers.is(orderRepository.findAll().get(0).getId())),
                        Matchers.hasProperty("totalPrice", Matchers.is(orderRepository.findAll().get(0).getTotalPrice())),
                        Matchers.hasProperty("date", Matchers.is(orderRepository.findAll().get(0).getDate())),
                        Matchers.hasProperty("status", Matchers.is(orderRepository.findAll().get(0).isStatus())),
                        Matchers.hasProperty("address", Matchers.is(orderRepository.findAll().get(0).getAddress()))
                ))))
                .andExpect(view().name("admin/orders"));
    }

    @Test
    public void getOrders_TryToGetPage_RedirectToLogin() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getCategories_TryToGetPage_AccessDenied() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void checkOrder_tryToGetOrderPage_isOk() throws Exception{
        Long id = 1L;
        mockMvc.perform(get("/admin/orders/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attribute("order", allOf(
                        Matchers.hasProperty("id", Matchers.is(orderRepository.findAll().get(0).getId())),
                        Matchers.hasProperty("totalPrice", Matchers.is(orderRepository.findAll().get(0).getTotalPrice())),
                        Matchers.hasProperty("status", Matchers.is(orderRepository.findAll().get(0).isStatus())),
                        Matchers.hasProperty("address", Matchers.is(orderRepository.findAll().get(0).getAddress()))
                )))
                .andExpect(view().name("admin/order-page"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void checkOrder_tryToGetOrderPage_isOkNotExists() throws Exception{
        Long id = 10L;
        mockMvc.perform(get("/admin/orders/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/orders"));

        assertTrue(orderRepository.findById(id).isEmpty());
    }

    @Test
    public void checkOrder_tryToGetOrderPage_redirect() throws Exception{
        Long id = 1L;
        mockMvc.perform(get("/admin/orders/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void checkOrder_tryToGetOrderPage_accessDenied() throws Exception{
        Long id = 1L;
        mockMvc.perform(get("/admin/orders/" + id))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void confirmOrder_tryToConfirmOrder_isOk() throws Exception {
        Long id = 1L;
        mockMvc.perform(patch("/admin/orders/" + id + "/confirm"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/orders"));

        assertTrue(orderRepository.findById(id).get().isStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void confirmOrder_tryToConfirmNotAvailableOrder_isOkNotExists() throws Exception {
        Long id = 10L;
        mockMvc.perform(patch("/admin/orders/" + id + "/confirm"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/orders"));

        assertTrue(orderRepository.findById(id).isEmpty());
    }

    @Test
    public void confirmOrder_tryToConfirmOrder_redirect() throws Exception {
        Long id = 1L;
        mockMvc.perform(patch("/admin/orders/" + id + "/confirm"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void confirmOrder_tryToConfirmOrder_accessDenied() throws Exception {
        Long id = 1L;
        mockMvc.perform(patch("/admin/orders/" + id + "/confirm"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }
}