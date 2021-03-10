package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.RegistrationDto;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetLoginPage_tryToGetLoginPage_loginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("util/login"));
    }

    @Test
    public void testGetRegistrationPage_tryToGetRegistrationPage_registrationPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(model().attribute("userForm", new RegistrationDto()))
                .andExpect(status().isOk())
                .andExpect(view().name("util/registration"));
    }

    @Test
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void testAccessDeniedPage_tryToGetAdminPageFromUser_accessDeniedPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    public void testAccessDeniedPage_tryToGetAccessDenied_accessDeniedPage() throws Exception {
        mockMvc.perform(get("/access-denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("util/access-denied"));
    }

    @Test
    public void testLogin_tryToLogin_Success() throws Exception {
        mockMvc.perform(post("/login")
                .param("j_username", "root")
                .param("j_password", "root"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    public void testLogin_tryToLogin_Forbidden() throws Exception {
        mockMvc.perform(post("/login").param("user", "user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void makeRegistration_tryToRegistration_success() throws Exception {
        mockMvc.perform(post("/registration")
                .param("login", "GoodLogin")
                .param("password", "GoodPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void makeRegistration_tryToRegistration_UserWithThisLoginExists() throws Exception {
        mockMvc.perform(post("/registration")
                .param("login", "root")
                .param("password", "GoodPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("util/registration"));
    }

    @Test
    public void makeRegistration_tryToRegistrationEmpty_error() throws Exception {
        mockMvc.perform(post("/registration")
                .param("login", "      ")
                .param("password", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("util/registration"));
    }
}