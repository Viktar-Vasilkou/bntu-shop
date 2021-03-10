package by.bntu.fitr.povt.vasilkou.bntu_shop.controller.admin;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CategoryService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.FileService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private static final String PRODUCT_ADDED_MSG = "Product added successfully";
    private static final String PRODUCT_UPDATED_MSG = "Product updated successfully";
    private static final String PRODUCT_DELETED_MSG = "Product deleted successfully";

    private final ProductService productService;
    private final CategoryService categoryService;
    private final FileService fileService;

    public AdminProductController(ProductService productService, CategoryService categoryService, FileService fileService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String getProducts(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Product> productPage = productService.getAll(page);
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("products", productPage.toList());
        model.addAttribute("categories", categoryService.getAll());
        if (!model.containsAttribute("newProduct")){
            model.addAttribute("newProduct", new Product());
        }
        return "admin/products";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute @Valid Product product,
                                BindingResult bindingResult,
                                @RequestParam Category category,
                                @RequestParam MultipartFile file,
                                RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()){
            bindingResult.addError(new FieldError("product", "fileName", "Please add photo"));
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newProduct", product);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newProduct", bindingResult);
            return "redirect:/admin/products";
        }
        product.setCategory(category);
        product.setFileName(fileService.uploadFile(file));

        if (productService.save(product) != null) {
            redirectAttributes.addFlashAttribute("msg", PRODUCT_ADDED_MSG);
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        if(!model.containsAttribute("product")){
            Product product = productService.getById(id);
            if (product.equals(new Product())){
                return "redirect:/admin/products";
            }
            model.addAttribute("product", product);
        }
        model.addAttribute("categories", categoryService.getAll());
        return "admin/product-edit";
    }

    @PatchMapping("/{id}")
    public String updateProduct(@PathVariable("id") Product productDb,
                                @ModelAttribute @Valid Product product,
                                BindingResult bindingResult,
                                @RequestParam(required = false) MultipartFile file,
                                RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", productDb);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            return "redirect:/admin/products/" + product.getId();
        }

        if(file != null){
            if (!file.isEmpty()) {
                fileService.deleteFile(productDb.getFileName());
                productDb.setFileName(fileService.uploadFile(file));
            }
        }

        productDb.setName(product.getName());
        productDb.setDescription(product.getDescription());
        productDb.setAmount(product.getAmount());
        productDb.setCost(product.getCost());
        productDb.setCategory(product.getCategory());
        productService.save(productDb);
        redirectAttributes.addFlashAttribute("msg", PRODUCT_UPDATED_MSG);
        return "redirect:/admin/products";
    }

    @PatchMapping("/{id}/activate")
    public String activateProduct(@PathVariable("id") Product product) {
        productService.save(productService.activate(product));
        return "redirect:/admin/products";
    }

    @PatchMapping("/{id}/deactivate")
    public String deactivateProduct(@PathVariable("id") Product product) {
        productService.save(productService.deactivate(product));
        return "redirect:/admin/products";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Product product, RedirectAttributes redirectAttributes) throws IOException {
        String fileName = product.getFileName();
        productService.delete(product);
        fileService.deleteFile(fileName);
        redirectAttributes.addFlashAttribute("msg", PRODUCT_DELETED_MSG);
        return "redirect:/admin/products";
    }
}
