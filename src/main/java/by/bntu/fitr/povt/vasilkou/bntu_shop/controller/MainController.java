package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping({"/", "/index", "/home"})
    public String getIndexPage() {
        return "index";
    }
}
