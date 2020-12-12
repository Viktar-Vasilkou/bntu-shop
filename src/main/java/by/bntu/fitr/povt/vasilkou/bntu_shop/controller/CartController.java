package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.OrderDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        model.addAttribute("orderForm", new OrderDto());
        return "user/cart";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam("id") Product product,
                             @RequestParam("amount") int amount) {
        cartService.addCartItem(product, amount);
        return "redirect:/products";
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam("id") Product product) {
        cartService.removeCartItem(product);
        return "redirect:/cart";
    }
}
