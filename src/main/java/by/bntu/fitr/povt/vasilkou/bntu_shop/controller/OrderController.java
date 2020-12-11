package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.OrderDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Order;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.OrderItem;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.security.MyUserDetails;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CartService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.OrderItemService;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @GetMapping
    public String getOrders(@AuthenticationPrincipal MyUserDetails userDetails,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                            Model model) {
        model.addAttribute("orders", orderService.getAllByUser(page, userDetails.getUser()));
        return "user/orders";
    }

    @PostMapping("/create")
    public String makeOrder(@AuthenticationPrincipal MyUserDetails userDetails,
                            @ModelAttribute("order") OrderDto orderDto) throws Exception {
        List<OrderItem> cart = orderItemService.getOrderItemsFromCart(cartService.getCart());
        User user = userDetails.getUser();
        Order order = orderService.createOrder(cart, user, orderDto, cartService.getTotalPrice());
        orderService.save(order);
        cart.forEach(p -> p.setOrder(order));
        orderItemService.saveAll(cart);
        cartService.checkout();
        return "redirect:/products";
    }
}
