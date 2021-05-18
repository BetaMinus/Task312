package com.beta.controller;

import com.beta.model.Role;
import com.beta.model.User;
import com.beta.service.RoleService;
import com.beta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public IndexController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(path = "")
    public String getIndex(Model model, Principal principal) {
        model.addAttribute("loggedInUser", (User) ((Authentication) principal).getPrincipal());
        model.addAttribute("usersList", userService.readAll());
        model.addAttribute("newUser", new User());
        return "index.html";
    }

    @PostMapping(path = "/admin/create")
    public String create(@ModelAttribute("user") User user, HttpServletRequest request) {
        String[] userRoles = request.getParameterValues("userRoles");
        List<Role> list = new ArrayList<>();
        for(String role: userRoles) {
            list.add(roleService.findByRoleName(role).get());
        }
        user.setRoles(list);
        userService.create(user);
        return "redirect:/";
    }

    @PostMapping(path = "/admin/edit")
    public String editUser(HttpServletRequest request) {
        User user = userService.readById(Integer.parseInt(request.getParameter("id"))).get();

        if (!request.getParameter("firstName").isEmpty()) {
            user.setFirstName(request.getParameter("firstName"));
        }
        if (!request.getParameter("lastName").isEmpty()) {
            user.setLastName(request.getParameter("lastName"));
        }
        if (!request.getParameter("age").isEmpty()) {
            user.setAge(Integer.parseInt(request.getParameter("age")));
        }
        if (!request.getParameter("email").isEmpty()) {
            user.setEmail(request.getParameter("email"));
        }
        String[] roles = request.getParameterValues("roles");
        if (roles != null) {
            List<Role> list = new ArrayList<>();
            for (String role: roles) {
                list.add(roleService.findByRoleName(role).get());
            }
            user.setRoles(list);
        }
        if (!request.getParameter("password").isEmpty()) {
            user.setPassword(request.getParameter("password"));
            userService.create(user);
        } else {
            userService.update(user);
        }
        return "redirect:/";
    }

    @PostMapping(path = "/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.readById(id).get());
        return "redirect:/";
    }
}
