package by.bntu.fitr.povt.vasilkou.bntu_shop.controller.admin;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdminCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getCategories_TryToGetPage_isOk() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categories"));
    }

    @Test
    public void getCategories_TryToGetPage_RedirectToLogin() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getCategories_TryToGetPage_AccessDenied() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createCategory_TryCreateNewUser_isOk() throws Exception {
        mockMvc.perform(post("/admin/categories/create")
                        .param("name", "Шорты"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createCategory_TryCreateNewUser_Error() throws Exception {
        Category category = new Category();
        category.setName("     ");

        mockMvc.perform(post("/admin/categories/create")
                .param("name", "     "))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("category", category))
                .andExpect(redirectedUrl("/admin/categories"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void updateCategory_tyToEditUser_isOk() throws Exception {
        mockMvc.perform(patch("/admin/categories")
                .param("id", "12")
                .param("name", "Шорты"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void updateCategory_tyToEditUser_Error() throws Exception {
        Category category = new Category();
        category.setId(12L); category.setName("      ");

        mockMvc.perform(patch("/admin/categories")
                .param("id", "12")
                .param("name", "      "))
                .andExpect(flash().attribute("category", category))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Transactional
    public void deleteCategory_isOk() throws Exception {
        mockMvc.perform(delete("/admin/categories/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }
}