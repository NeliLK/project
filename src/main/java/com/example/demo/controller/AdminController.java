package com.example.demo.controller;

import com.example.demo.model.dto.UserViewModel;
import com.example.demo.model.entity.UserRoleEntity;
import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("admin-user-all");

        List<UserViewModel> users = userService.getAllUsers();

        modelAndView.addObject("users", users);

        return modelAndView;
    }

    @GetMapping("/user/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return new ModelAndView("redirect:/admin/user");
    }

    @PostMapping("/user/add/role")
    public ModelAndView addRole(@RequestParam Long userId, @RequestParam String newRole) {
        UserRoleEnum roleEnum = UserRoleEnum.valueOf(newRole);
        userService.addRoleToUser(userId, roleEnum);
        return new ModelAndView("redirect:/admin/user");
    }

    @PostMapping("/user/remove/role")
    public ModelAndView removeRole(@RequestParam Long userId, @RequestParam String removeRole) {
        UserRoleEnum roleEnum = UserRoleEnum.valueOf(removeRole);
        userService.removeRoleFromUser(userId, roleEnum);
        return new ModelAndView("redirect:/admin/user");
    }
}
