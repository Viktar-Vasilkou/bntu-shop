package by.bntu.fitr.povt.vasilkou.bntu_shop.controller.admin;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.CategoryRepository;
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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getCategories_TryToGetPage_isOk() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", Matchers.hasItem(allOf(
                        Matchers.hasProperty("id", Matchers.is(categoryRepository.findAll().get(0).getId())),
                        Matchers.hasProperty("name", Matchers.is(categoryRepository.findAll().get(0).getName()))
                ))))
                .andExpect(view().name("admin/categories"));
    }

    @Test
    public void getCategories_TryToGetPage_RedirectToLogin() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "user")
    public void getCategories_TryToGetPage_AccessDenied() throws Exception {
        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createCategory_TryCreateNewCategory_isOk() throws Exception {
        String name = "Шорты";
        mockMvc.perform(post("/admin/categories/create")
                .param("name", name))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        assertEquals(2, categoryRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void createCategory_TryCreateNewCategory_Error() throws Exception {
        Category category = new Category();
        category.setName("     ");

        mockMvc.perform(post("/admin/categories/create")
                .param("name", "     "))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("newCategory", category))
                .andExpect(redirectedUrl("/admin/categories"));

        assertTrue(categoryRepository.findById(2L).isEmpty());
        assertEquals(1, categoryRepository.findAll().size());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateCategory_tyToEditUser_isOk() throws Exception {
        Long id = 1L;
        String newName = "newName";

        mockMvc.perform(patch("/admin/categories")
                .param("id", String.valueOf(id))
                .param("name", newName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        assertEquals(newName, categoryRepository.findById(id).get().getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateCategory_tyToEditUser_Error() throws Exception {
        Long id = 1L;
        String newName = "";

        mockMvc.perform(patch("/admin/categories")
                .param("id", String.valueOf(id))
                .param("name", newName))
                .andExpect(flash().attribute("category",
                        allOf(
                                Matchers.hasProperty("id", Matchers.is(id)),
                                Matchers.hasProperty("name", Matchers.is(newName))
                        )
                ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        assertNotEquals(newName, categoryRepository.findById(id).get().getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deleteCategory_isOk() throws Exception {
        mockMvc.perform(delete("/admin/categories/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        assertEquals(0, categoryRepository.findAll().size());
    }
}