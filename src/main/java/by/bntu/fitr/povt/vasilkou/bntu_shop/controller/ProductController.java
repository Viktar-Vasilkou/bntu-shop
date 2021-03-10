package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CategoryService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String getProductsPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(name = "category", required = false) Category category,
                                  Model model) {
        Page<Product> productPage = productService.getAllAvailable(page, category);
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalElements", productPage.getTotalElements());
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("categories", categoryService.getAll());
        return "products/products";
    }

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable("id") Long id, Model model) {
        if(productService.getById(id).equals(new Product())){
            return "redirect:/products";
        }
        model.addAttribute("product", productService.getById(id));
        return "products/product-page";
    }
}
