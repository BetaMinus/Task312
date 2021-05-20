package com.beta.controller;

import com.beta.model.User;
import com.beta.service.RoleService;
import com.beta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(path = "")
    public String getIndex(Model model, Principal principal) {
        model.addAttribute("loggedInUser", (User) ((Authentication) principal).getPrincipal());
        model.addAttribute("usersList", userService.readAll());
        return "index.html";
    }

    @PostMapping(path = "/admin/create")
    public String create(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("id", request.getParameter("id"));
        params.put("firstName",request.getParameter("firstName"));
        params.put("lastName",request.getParameter("lastName"));
        params.put("age",request.getParameter("age"));
        params.put("email",request.getParameter("email"));
        params.put("password",request.getParameter("password"));
        String[] userRoles = request.getParameterValues("userRoles");
        userService.create(params, userRoles);
        return "redirect:/";
    }

    @PostMapping(path = "/admin/edit")
    public String editUser(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("id", request.getParameter("id"));
        params.put("firstName",request.getParameter("firstName"));
        params.put("lastName",request.getParameter("lastName"));
        params.put("age",request.getParameter("age"));
        params.put("email",request.getParameter("email"));
        params.put("password",request.getParameter("password"));
        String[] roles = request.getParameterValues("roles");
        userService.update(params, roles);
        return "redirect:/";
    }

    @PostMapping(path = "/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(userService.readById(id).get());
        return "redirect:/";
    }
}
