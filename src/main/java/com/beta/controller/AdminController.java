//package com.beta.controller;
//
//import com.beta.model.Role;
//import com.beta.model.User;
//import com.beta.service.RoleService;
//import com.beta.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping(path = "/admin")
//public class AdminController {
//    private final RoleService roleService;
//    private final UserService userService;
//
//    @Autowired
//    public AdminController(RoleService roleService, UserService userService) {
//        this.roleService = roleService;
//        this.userService = userService;
//    }
//    //------------------------------------------------------------------------------------------------------------------
//    @GetMapping(
//            path = "")
//    public String adminPage() {
//        return "/admin/adminPage";
//    }
//    //------------------------------------------------------------------------------------------------------------------
//    @GetMapping(
//            path = "/users/all")
//    public String getUsersList(Model model) {
//        model.addAttribute("usersList", userService.readAll());
//        return "/admin/usersList";
//    }
//    ------------------------------------------------------------------------------------------------------------------
//    @GetMapping(
//            path = "/users/new")
//    public String createUser(Model model) {
//        model.addAttribute("user", new User());
//        return "/admin/createUser";
//    }
//
//    @PostMapping(path = "/users/create")
//    public String create(@ModelAttribute("user") User user, HttpServletRequest request) {
//        user.setPassword(request.getParameter("password"));
//        String[] userRoles = request.getParameterValues("userRoles");
//        List<Role> list = new ArrayList<>();
//        for(String role: userRoles) {
//            list.add(roleService.findByRoleName(role).get());
//        }
//        user.setRoles(list);
//        userService.create(user);
//        return "redirect:/index";
//    }
//    -------------------------------------------------------------------------------------------------------------------
//    @GetMapping(
//            path = "/users/{id}")
//    public String getUserById(@PathVariable("id") long id, Model model){
//        model.addAttribute("user", userService.readById(id).get());
//        return "admin/readUser";
//    }
//    ------------------------------------------------------------------------------------------------------------------
//    @GetMapping(
//            path = "/users/{id}/edit")
//        public String editUser(Model model, @PathVariable("id") long id) {
//            model.addAttribute("user", userService.readById(id).get());
//            return "/admin/editUser";
//        }
//
//    @PostMapping(
//            path = "/users/{id}/edit")
//    public String updateUser(@ModelAttribute("user") User user,
//                             @PathVariable("id") long id,
//                             HttpServletRequest request) {
//
//        if (request.getParameter("login").isEmpty()) {
//            user.setLogin(userService.readById(id).get().getLogin());
//        }
//
//        String[] userRoles = request.getParameterValues("userRoles");
//        if (userRoles == null) {
//            user.setRoles(userService.readById(id).get().getRoles());
//        } else {
//            List<Role> list = new ArrayList<>();
//            for (String role: userRoles) {
//                list.add(roleService.findByRoleName(role).get());
//            }
//            user.setRoles(list);
//        }
//
//        if (request.getParameter("password").isEmpty()) {
//            user.setPassword(userService.readById(id).get().getPassword());
//            userService.update(user);
//        } else {
//            user.setPassword(request.getParameter("password"));
//            userService.create(user);
//        }
//        return "redirect:/admin/users/all";
//    }
//    @PostMapping(path = "/users/{id}/edit")
//    public String editUser(@PathVariable("id") long id, HttpServletRequest request) {
//        User user = userService.readById(id).get();
//
//        if (!request.getParameter("firstName").isEmpty()) {
//            user.setFirstName(request.getParameter("firstName"));
//        }
//
//        if (!request.getParameter("lastName").isEmpty()) {
//            user.setLastName(request.getParameter("lastName"));
//        }
//
//        if (!request.getParameter("age").isEmpty()) {
//            user.setAge(Integer.parseInt(request.getParameter("age")));
//        }
//
//        if (!request.getParameter("email").isEmpty()) {
//            user.setEmail(request.getParameter("email"));
//        }
//
//        if (!request.getParameter("password").isEmpty()) {
//            user.setPassword(request.getParameter("password"));
//        }
//
//        String[] roles = request.getParameterValues("roles");
//        if (roles != null) {
//            List<Role> list = new ArrayList<>();
//            for (String role: roles) {
//                list.add(roleService.findByRoleName(role).get());
//            }
//            user.setRoles(list);
//        }
//        userService.create(user);
//        return "redirect:/index";
//    }
//
//    ------------------------------------------------------------------------------------------------------------------
//    @PostMapping(
//            path = "/users/{id}/delete")
//        public String deleteUser(@PathVariable("id") long id) {
//            userService.delete(userService.readById(id).get());
//            return "redirect:/index";
//        }
//}
