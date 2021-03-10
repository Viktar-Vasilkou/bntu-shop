package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    private static final User TEST_USER;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    static {
        TEST_USER = User.builder()
                .id(1L)
                .login("root")
                .name("Victor")
                .surname("Vasilkou")
                .phoneNumber("291571601")
                .build();
    }

    @Test
    @WithUserDetails(value = "root", userDetailsServiceBeanName = "userDetailsServiceImpl")
    public void testGetUserPage_tryToGetUserPage_userPage() throws Exception {
        mockMvc.perform(get("/account"))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", allOf(
                        hasProperty("login", is(TEST_USER.getLogin())),
                        hasProperty("name", is(TEST_USER.getName())),
                        hasProperty("surname", is(TEST_USER.getSurname())),
                        hasProperty("phoneNumber", is(TEST_USER.getPhoneNumber()))
                )))
                .andExpect(view().name("user/user-page"));

        assertNotNull(userRepository.findByLogin("root"));
    }


    @Test
    @WithUserDetails(value = "root", userDetailsServiceBeanName = "userDetailsServiceImpl")
    public void updateUser_tryWithUser_Updated() throws Exception {
        String updatedName = "updated-name";

        this.mockMvc.perform(patch("/account/edit")
                .param("id", String.valueOf(TEST_USER.getId()))
                .param("login", TEST_USER.getLogin())
                .param("name", updatedName)
                .param("surname", TEST_USER.getSurname())
                .param("phoneNumber", TEST_USER.getPhoneNumber()))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account"));

        assertEquals(updatedName, userRepository.findByLogin(TEST_USER.getLogin()).get().getName());
    }

    @Test
    @WithUserDetails(value = "root", userDetailsServiceBeanName = "userDetailsServiceImpl")
    public void updateUser_tryWithUser_FailUpdated() throws Exception {
        String updatedName = "        ";

        this.mockMvc.perform(patch("/account/edit")
                .param("id", String.valueOf(TEST_USER.getId()))
                .param("login", TEST_USER.getLogin())
                .param("name", updatedName)
                .param("surname", TEST_USER.getSurname())
                .param("phoneNumber", TEST_USER.getPhoneNumber()))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("user/user-page"));

        assertNotEquals(updatedName, userRepository.findByLogin(TEST_USER.getLogin()).get().getName());
    }
}