package by.bntu.fitr.povt.vasilkou.bntu_shop.controller.admin;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CategoryService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.FileService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl.CategoryServiceImpl;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private static final String CATEGORY_ADDED_MSG = "Category added successfully";
    private static final String CATEGORY_UPDATED_MSG = "Category updated successfully";
    private static final String CATEGORY_DELETED_MSG = "Category deleted successfully";

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ----------------------- Category --------------------------------------
    /*
        Добавить предупреждение, что после удаления, удаляться и все товары
     */

    @GetMapping()
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        if (!model.containsAttribute("newCategory")) {
            model.addAttribute("newCategory", new Category());
        }
        return "admin/categories";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute @Valid Category category,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("category", category);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", bindingResult);
        } else {
            if (categoryService.save(category) != null) {
                redirectAttributes.addFlashAttribute("msg", CATEGORY_ADDED_MSG);
            }
        }

        return "redirect:/admin/categories";
    }

    @PatchMapping()
    public String updateCategory(@ModelAttribute @Valid Category category,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("category", category);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newCategory", bindingResult);
        } else {
            if (categoryService.edit(category) != null) {
                redirectAttributes.addFlashAttribute("msg", CATEGORY_UPDATED_MSG);
            }
        }

        return "redirect:/admin/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes attributes) {
        Category category = categoryService.getById(id);
        attributes.addFlashAttribute("msg", CATEGORY_DELETED_MSG);
        categoryService.delete(category);
        return "redirect:/admin/categories";
    }
}
