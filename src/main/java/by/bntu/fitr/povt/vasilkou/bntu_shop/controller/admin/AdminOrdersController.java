package by.bntu.fitr.povt.vasilkou.bntu_shop.controller.admin;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Order;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrdersController {

    private final OrderService orderService;

    public AdminOrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                            Model model) {
        model.addAttribute("orders", orderService.getAll(page, false).getContent());
        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String checkOrder(@PathVariable("id") Order order, Model model) {
        if (order == null){
            return "redirect:/admin/orders";
        }
        model.addAttribute("order", order);
        return "admin/order-page";
    }

    @PatchMapping("/{id}/confirm")
    public String confirmOrder(@PathVariable("id") Order order) {
        if (order == null){
            return "redirect:/admin/orders";
        }
        orderService.save(orderService.confirm(order));
        return "redirect:/admin/orders";
    }
}
