package by.bntu.fitr.povt.vasilkou.bntu_shop.controller;

import by.bntu.fitr.povt.vasilkou.bntu_shop.dto.UserDto;
import by.bntu.fitr.povt.vasilkou.bntu_shop.mappers.api.UserMapper;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.User;
import by.bntu.fitr.povt.vasilkou.bntu_shop.security.MyUserDetails;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("")
    public String getUserPage(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", userMapper.toDto(user));
        return "user/user-page";
    }

    @PatchMapping("/edit")
    // todo mackeyb 10
    public String editUser(@AuthenticationPrincipal MyUserDetails userDetails,
                           @ModelAttribute("user") @Valid UserDto userDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/user-page";
        } else {
            userDetails.setUser(userService.save(userMapper.toEntity(userDto)));
        }
        return "redirect:/account";
    }
}
