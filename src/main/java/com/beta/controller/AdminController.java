package com.beta.controller;

import com.beta.model.User;
import com.beta.service.RoleService;
import com.beta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(path = "")
    public String getIndex(Model model, Principal principal) {
        model.addAttribute("loggedInUser", (User) ((Authentication) principal).getPrincipal());
        if (((Authentication) principal).getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Admin"))) {
            model.addAttribute("usersList", userService.readAll());
            model.addAttribute("newUser", new User());
        }
        return "index.html";
    }

    @PostMapping(path = "/admin/create")
    public String create(@ModelAttribute("newUser") User user, @RequestParam(value = "userRoles", required = true) String[] roles) {
        userService.create(user, roles);
        return "redirect:/";
    }

    @PostMapping(path = "/admin/edit")
    public String testEditUser(@ModelAttribute("user") User user,
                               @RequestParam(value = "userRoles", required = false) String[] roles) {
        userService.update(user, roles);
        return "redirect:/";
    }

    @PostMapping(path = "/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.readById(id).get());
        return "redirect:/";
    }
}
