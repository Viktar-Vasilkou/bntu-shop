package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.RegistrationDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.security.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class LoginController {

    private final UserDetailsServiceImpl userDetailsService;

    public LoginController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "util/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new RegistrationDto());
        return "util/registration";
    }

    @PostMapping("/registration")
    public String makeRegistration(@ModelAttribute("userForm") @Valid RegistrationDto user,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "util/registration";
        }

        if (!userDetailsService.saveNewUser(user)) {
            bindingResult.addError(new FieldError("userForm", "login",
                    "User with this login is already exists"));
            return "util/registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "util/access-denied";
    }
}
