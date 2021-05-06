package com.beta.controller;

import com.beta.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @GetMapping(
            path = "/userpage")
    public String getUser(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser);
        return "/user/userPage";
    }
}
